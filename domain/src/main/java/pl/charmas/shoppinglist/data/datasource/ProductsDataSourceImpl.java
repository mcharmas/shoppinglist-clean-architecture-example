package pl.charmas.shoppinglist.data.datasource;

import java.util.ArrayList;
import javax.inject.Inject;
import pl.charmas.shoppinglist.data.entity.ProductEntity;
import pl.charmas.shoppinglist.data.entity.mappers.ProductEntityMapper;
import pl.charmas.shoppinglist.data.store.ProductEntityStore;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;

public class ProductsDataSourceImpl implements ProductsDataSource {
  private final ProductEntityStore productEntityStore;
  private final ProductEntityMapper mapper;

  @Inject
  public ProductsDataSourceImpl(ProductEntityStore productEntityStore, ProductEntityMapper mapper) {
    this.productEntityStore = productEntityStore;
    this.mapper = mapper;
  }

  @Override public ProductList getProductList() {
    ArrayList<Product> products = new ArrayList<>();
    for (ProductEntity productEntity : productEntityStore.getAllProduct()) {
      products.add(mapper.toProduct(productEntity));
    }
    return new ProductList(products);
  }

  @Override public void saveProductList(ProductList products) {
    ArrayList<ProductEntity> entities = new ArrayList<>();
    for (Product product : products) {
      entities.add(mapper.toEntity(product));
    }
    productEntityStore.storeAllProducts(entities);
  }
}
