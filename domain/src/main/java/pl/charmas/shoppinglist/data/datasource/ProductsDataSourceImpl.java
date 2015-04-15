package pl.charmas.shoppinglist.data.datasource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.data.entity.ProductEntity;
import pl.charmas.shoppinglist.data.entity.mappers.ProductEntityMapper;
import pl.charmas.shoppinglist.data.store.ProductEntityStore;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;

public class ProductsDataSourceImpl implements ProductsDataSource {
  private final ProductEntityStore productEntityStore;
  private final ProductEntityMapper mapper;

  @Inject
  public ProductsDataSourceImpl(ProductEntityStore productEntityStore, ProductEntityMapper mapper) {
    this.productEntityStore = productEntityStore;
    this.mapper = mapper;
  }

  @Override
  public Product createProduct(String name, boolean isBought) {
    ProductEntity newEntity = new ProductEntity(productEntityStore.getCreateNextId(), name, isBought);
    List<ProductEntity> products = getAll();
    products.add(newEntity);
    productEntityStore.storeAllProducts(products);
    return mapper.toProduct(newEntity);
  }

  @Override
  public int removeProduct(long id) {
    ArrayList<ProductEntity> entities = getAll();
    Iterator<ProductEntity> iterator = entities.iterator();
    int counter = 0;
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        counter++;
      }
    }
    productEntityStore.storeAllProducts(entities);
    return counter;
  }

  @Override
  public Product updateProduct(Product productToUpdate) {
    ArrayList<ProductEntity> entities = getAll();
    for (int i = 0; i < entities.size(); i++) {
      if (entities.get(i).getId() == productToUpdate.getId()) {
        entities.set(i, mapper.toEntity(productToUpdate));
      }
    }
    productEntityStore.storeAllProducts(entities);
    return productToUpdate;
  }

  @Override
  public List<Product> listAll() {
    return mapper.toProduct(getAll());
  }

  @Override
  public Product getProduct(long id) {
    for (ProductEntity entity : getAll()) {
      if (entity.getId() == id) {
        return mapper.toProduct(entity);
      }
    }
    return null;
  }

  private ArrayList<ProductEntity> getAll() {
    return new ArrayList<>(productEntityStore.getAllProduct());
  }
}
