package pl.charmas.shoppinglist.data.store;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;

public class SharedPreferencesProductsDataSource implements ProductsDataSource {
  private static final String SP_PRODUCT_ENTITIES = "PRODUCT_ENTITIES";
  private final SharedPreferences sharedPreferences;
  private final ProductListJsonMapper productListJsonMapper;

  @Inject
  public SharedPreferencesProductsDataSource(SharedPreferences sharedPreferences,
      ProductListJsonMapper productListJsonMapper) {
    this.sharedPreferences = sharedPreferences;
    this.productListJsonMapper = productListJsonMapper;
  }

  @Override public ProductList getProductList() {
    try {
      return productListJsonMapper.fromJson(sharedPreferences.getString(SP_PRODUCT_ENTITIES, "[]"));
    } catch (JSONException e) {
      handleJSONError(e);
      return new ProductList(Collections.<Product>emptyList());
    }
  }

  @Override public void saveProductList(ProductList products) {
    try {
      sharedPreferences.edit()
          .putString(SP_PRODUCT_ENTITIES, productListJsonMapper.toJson(products))
          .apply();
    } catch (JSONException ex) {
      handleJSONError(ex);
    }
  }

  private void handleJSONError(JSONException e) {
    sharedPreferences.edit().remove(SP_PRODUCT_ENTITIES).apply();
    throw new RuntimeException("Error reading entities. Clearing database.", e);
  }

  public static class ProductListJsonMapper {
    @Inject public ProductListJsonMapper() {
    }

    private JSONObject toJson(Product entity) throws JSONException {
      JSONObject obj = new JSONObject();
      obj.put("id", entity.getId());
      obj.put("name", entity.getName());
      obj.put("isBought", entity.isBought());
      return obj;
    }

    private Product fromJson(JSONObject obj) throws JSONException {
      return new Product(obj.getLong("id"), obj.getString("name"), obj.getBoolean("isBought"));
    }

    public ProductList fromJson(String content) throws JSONException {
      JSONArray arrays = new JSONArray(content);
      ArrayList<Product> entities = new ArrayList<>(arrays.length());
      for (int i = 0; i < arrays.length(); i++) {
        entities.add(fromJson(arrays.getJSONObject(i)));
      }
      return new ProductList(entities);
    }

    public String toJson(ProductList entities) throws JSONException {
      JSONArray jsonArray = new JSONArray();
      for (Product entity : entities) {
        jsonArray.put(toJson(entity));
      }
      return jsonArray.toString();
    }
  }
}
