package pl.charmas.shoppinglist.data.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;

@Singleton
public class InMemoryProductDataSource implements ProductsDataSource {
  private ProductList productList = new ProductList(Collections.<Product>emptyList());

  @Inject
  public InMemoryProductDataSource() {
    List<Product> entities = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      entities.add(new Product(i, "Product " + i, false));
    }
    saveProductList(new ProductList(entities));
  }

  @Override public ProductList getProductList() {
    return productList;
  }

  @Override public void saveProductList(ProductList products) {
    this.productList = products;
  }
}
