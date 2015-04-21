package pl.charmas.shoppinglist.data.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

@Singleton
public class InMemoryProductEntityStore implements ProductEntityStore {
  private List<ProductEntity> entities = Collections.emptyList();

  @Inject
  public InMemoryProductEntityStore() {
    List<ProductEntity> entities = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      entities.add(new ProductEntity(i, "Product " + i, false));
    }
    storeAllProducts(entities);
  }

  @Override public List<ProductEntity> getAllProduct() {
    return entities;
  }

  @Override public void storeAllProducts(List<ProductEntity> entities) {
    this.entities = entities;
  }
}
