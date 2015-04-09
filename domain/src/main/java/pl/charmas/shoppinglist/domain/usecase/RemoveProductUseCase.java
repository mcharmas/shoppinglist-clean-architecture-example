package pl.charmas.shoppinglist.domain.usecase;

import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;

public class RemoveProductUseCase implements UseCase<Integer, Long> {
  private final ProductsDataSource productsDataSource;

  @Inject
  public RemoveProductUseCase(ProductsDataSource productsDataSource) {
    this.productsDataSource = productsDataSource;
  }

  @Override
  public Integer execute(final Long productId) {
    return productsDataSource.removeProduct(productId);
  }
}
