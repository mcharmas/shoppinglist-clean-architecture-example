package pl.charmas.shoppinglist.domain.usecase;

import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;

public class ListProductsUseCase implements UseCaseArgumentless<List<Product>> {
  private final ProductsDataSource productsDataSource;

  @Inject
  public ListProductsUseCase(ProductsDataSource productsDataSource) {
    this.productsDataSource = productsDataSource;
  }

  @Override
  public List<Product> execute() throws Exception {
    Thread.sleep(5000);
    return productsDataSource.listAll();
  }
}
