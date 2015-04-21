package pl.charmas.shoppinglist.domain.datasource;

import pl.charmas.shoppinglist.domain.entities.ProductList;

public interface ProductsDataSource {
  ProductList getProductList();
  void saveProductList(ProductList products);
}
