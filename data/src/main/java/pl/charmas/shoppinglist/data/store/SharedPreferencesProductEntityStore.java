package pl.charmas.shoppinglist.data.store;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

public class SharedPreferencesProductEntityStore implements ProductEntityStore {
  private static final String SP_CURRENT_ID = "CURRENT_ID";
  private static final String SP_PRODUCT_ENTITIES = "PRODUCT_ENTITIES";
  private final SharedPreferences sharedPreferences;
  private final EntityJsonMapper entityJsonMapper;

  @Inject
  public SharedPreferencesProductEntityStore(SharedPreferences sharedPreferences,
      EntityJsonMapper entityJsonMapper) {
    this.sharedPreferences = sharedPreferences;
    this.entityJsonMapper = entityJsonMapper;
  }

  @Override public ProductEntity createProductEntity(String name, boolean isBought) {
    ProductEntity entity = new ProductEntity(getNextId(), name, isBought);
    List<ProductEntity> entities = listAll();
    entities.add(entity);
    updateAll(entities);
    return entity;
  }

  private void updateAll(List<ProductEntity> entities) {
    try {
      sharedPreferences.edit().putString(SP_PRODUCT_ENTITIES, entityJsonMapper.toJson(entities)).apply();
    } catch (JSONException e) {
      handleJSONError(e);
    }
  }

  private void handleJSONError(JSONException e) {
    sharedPreferences.edit().remove(SP_PRODUCT_ENTITIES).apply();
    throw new RuntimeException("Error reading entities. Clearing database.", e);
  }

  private long getNextId() {
    int id = sharedPreferences.getInt(SP_CURRENT_ID, 0);
    sharedPreferences.edit().putInt(SP_CURRENT_ID, id + 1).apply();
    return id;
  }

  @Override public int removeProductEntity(long id) {
    List<ProductEntity> productEntities = listAll();
    Iterator<ProductEntity> iterator = productEntities.iterator();
    int removeCounter = 0;
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        removeCounter++;
      }
    }
    updateAll(productEntities);
    return removeCounter;
  }

  @Override public ProductEntity updateProductEntity(ProductEntity productToUpdate) {
    List<ProductEntity> entities = listAll();
    int location = -1;
    for (int i = 0; i < entities.size(); i++) {
      if (entities.get(i).getId() == productToUpdate.getId()) {
        location = i;
        break;
      }
    }
    if (location != -1) {
      entities.set(location, productToUpdate);
      updateAll(entities);
    }
    return productToUpdate;
  }

  @Override public List<ProductEntity> listAll() {
    try {
      return entityJsonMapper.fromJson(sharedPreferences.getString(SP_PRODUCT_ENTITIES, "[]"));
    } catch (JSONException e) {
      handleJSONError(e);
      return Collections.emptyList();
    }
  }

  @Override public ProductEntity getProduct(long id) {
    for (ProductEntity entity : listAll()) {
      if (entity.getId() == id) {
        return entity;
      }
    }
    return null;
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
