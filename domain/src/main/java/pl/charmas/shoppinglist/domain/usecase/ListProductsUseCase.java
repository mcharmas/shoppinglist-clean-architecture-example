package pl.charmas.shoppinglist.domain.usecase;

import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.ProductList;

public class ListProductsUseCase implements UseCaseArgumentless<ProductList> {
  private final ProductsDataSource productsDataSource;

  @Inject
  public ListProductsUseCase(ProductsDataSource productsDataSource) {
    this.productsDataSource = productsDataSource;
  }

  @Override
  public ProductList execute() throws Exception {
    Thread.sleep(500);
    return productsDataSource.getProductList();
  }
}
