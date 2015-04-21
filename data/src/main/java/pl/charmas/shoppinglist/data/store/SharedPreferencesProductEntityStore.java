package pl.charmas.shoppinglist.data.store;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

public class SharedPreferencesProductEntityStore implements ProductEntityStore {
  private static final String SP_PRODUCT_ENTITIES = "PRODUCT_ENTITIES";
  private final SharedPreferences sharedPreferences;
  private final EntityJsonMapper entityJsonMapper;

  @Inject
  public SharedPreferencesProductEntityStore(SharedPreferences sharedPreferences,
      EntityJsonMapper entityJsonMapper) {
    this.sharedPreferences = sharedPreferences;
    this.entityJsonMapper = entityJsonMapper;
  }

  private void handleJSONError(JSONException e) {
    sharedPreferences.edit().remove(SP_PRODUCT_ENTITIES).apply();
    throw new RuntimeException("Error reading entities. Clearing database.", e);
  }

  @Override public List<ProductEntity> getAllProduct() {
    try {
      return entityJsonMapper.fromJson(sharedPreferences.getString(SP_PRODUCT_ENTITIES, "[]"));
    } catch (JSONException e) {
      handleJSONError(e);
      return Collections.emptyList();
    }
  }

  @Override public void storeAllProducts(List<ProductEntity> entities) {
    try {
      sharedPreferences.edit().putString(SP_PRODUCT_ENTITIES, entityJsonMapper.toJson(entities)).apply();
    } catch (JSONException e) {
      handleJSONError(e);
    }
  }

  public static class EntityJsonMapper {
    @Inject public EntityJsonMapper() {
    }

    private JSONObject toJson(ProductEntity entity) throws JSONException {
      JSONObject obj = new JSONObject();
      obj.put("id", entity.getId());
      obj.put("name", entity.getName());
      obj.put("isBought", entity.isBought());
      return obj;
    }

    private ProductEntity fromJson(JSONObject obj) throws JSONException {
      return new ProductEntity(obj.getLong("id"), obj.getString("name"), obj.getBoolean("isBought"));
    }

    public List<ProductEntity> fromJson(String content) throws JSONException {
      JSONArray arrays = new JSONArray(content);
      ArrayList<ProductEntity> entities = new ArrayList<>(arrays.length());
      for (int i = 0; i < arrays.length(); i++) {
        entities.add(fromJson(arrays.getJSONObject(i)));
      }
      return entities;
    }

    public String toJson(List<ProductEntity> entities) throws JSONException {
      JSONArray jsonArray = new JSONArray();
      for (ProductEntity entity : entities) {
        jsonArray.put(toJson(entity));
      }
      return jsonArray.toString();
    }
  }
}
