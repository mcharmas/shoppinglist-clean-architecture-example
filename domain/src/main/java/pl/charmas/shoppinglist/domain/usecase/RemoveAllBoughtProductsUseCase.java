package pl.charmas.shoppinglist.domain.usecase;

import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.ProductList;

public class RemoveAllBoughtProductsUseCase implements UseCaseArgumentless<Integer> {
  private final ProductsDataSource productsDataSource;

  @Inject
  public RemoveAllBoughtProductsUseCase(ProductsDataSource productsDataSource) {
    this.productsDataSource = productsDataSource;
  }

  public Integer execute() {
    ProductList productList = productsDataSource.getProductList();
    int removedCount = productList.removeBoughtProducts();
    productsDataSource.saveProductList(productList);
    return removedCount;
  }
}
