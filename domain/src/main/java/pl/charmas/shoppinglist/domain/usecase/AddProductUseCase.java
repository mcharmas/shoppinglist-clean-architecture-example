package pl.charmas.shoppinglist.domain.usecase;

import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;

public class AddProductUseCase implements UseCase<Product, String> {
  private final ProductsDataSource productsDataSource;

  @Inject
  public AddProductUseCase(ProductsDataSource productsDataSource) {
    this.productsDataSource = productsDataSource;
  }

  @Override
  public Product execute(final String productName) {
    if (productName == null || productName.trim().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be empty");
    }
    ProductList productList = productsDataSource.getProductList();
    Product product = productList.addProduct(productName);
    productsDataSource.saveProductList(productList);
    return product;
  }
}
