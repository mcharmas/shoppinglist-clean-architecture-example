package pl.charmas.shoppinglist.data.store;

import java.util.List;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

public interface ProductEntityStore {
  List<ProductEntity> getAllProduct();

  void storeAllProducts(List<ProductEntity> entities);
}
