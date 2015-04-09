package pl.charmas.shoppinglist.data.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

@Singleton
public class InMemoryProductEntityStore implements ProductEntityStore {
  private final Map<Long, ProductEntity> products = new HashMap<>();
  private long currentId = 0;

  @Inject
  public InMemoryProductEntityStore() {
    for (int i = 0; i < 20; i++) {
      createProductEntity("Product " + i, false);
    }
  }

  @Override
  public ProductEntity createProductEntity(String name, boolean isBought) {
    ProductEntity productGateway = new ProductEntity(currentId, name, isBought);
    currentId++;
    products.put(productGateway.getId(), productGateway);
    return productGateway;
  }

  @Override
  public int removeProductEntity(long id) {
    return products.remove(id) != null ? 1 : 0;
  }

  @Override
  public ProductEntity updateProductEntity(ProductEntity productToUpdate) {
    products.remove(productToUpdate.getId());
    products.put(productToUpdate.getId(), productToUpdate);
    return productToUpdate;
  }

  @Override
  public List<ProductEntity> listAll() {
    return new ArrayList<>(products.values());
  }

  @Override
  public ProductEntity getProduct(long id) {
    return products.get(id);
  }

}
