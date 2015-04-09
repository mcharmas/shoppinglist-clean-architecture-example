package pl.charmas.shoppinglist.domain.usecase;

import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;

public class RemoveAllBoughtProductsUseCase implements UseCaseArgumentless<Integer> {
  private final RemoveProductUseCase removeProductUseCase;
  private final ProductsDataSource productsDataSource;

  @Inject
  public RemoveAllBoughtProductsUseCase(RemoveProductUseCase removeProductUseCase,
      ProductsDataSource productsDataSource) {
    this.removeProductUseCase = removeProductUseCase;
    this.productsDataSource = productsDataSource;
  }

  public Integer execute() {
    List<Product> allProducts = productsDataSource.listAll();
    int removedProductsCount = 0;
    for (Product products : allProducts) {
      if (products.isBought()) {
        removedProductsCount += removeProductUseCase.execute(products.getId());
      }
    }

    return removedProductsCount;
  }
}
