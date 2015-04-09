package pl.charmas.shoppinglist.domain.datasource;

import java.util.List;
import pl.charmas.shoppinglist.domain.entities.Product;

public interface ProductsDataSource {
  Product createProduct(String name, boolean isBought);

  int removeProduct(long id);

  Product updateProduct(Product productToUpdate);

  List<Product> listAll();

  Product getProduct(long id);
}
