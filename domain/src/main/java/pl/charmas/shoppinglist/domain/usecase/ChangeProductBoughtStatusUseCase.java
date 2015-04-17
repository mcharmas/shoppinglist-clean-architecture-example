package pl.charmas.shoppinglist.domain.usecase;

import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;

public class ChangeProductBoughtStatusUseCase implements UseCase<Void, ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest> {
  private final ProductsDataSource productsDataSource;

  @Inject
  public ChangeProductBoughtStatusUseCase(ProductsDataSource productsDataSource) {
    this.productsDataSource = productsDataSource;
  }

  @Override
  public Void execute(final ChangeProductStatusRequest request) {
    ProductList productList = productsDataSource.getProductList();
    Product currentProduct = productList.getProduct(request.productId);
    if (request.boughtStatus) {
      currentProduct.markBought();
    } else {
      currentProduct.markNotBought();
    }
    productsDataSource.saveProductList(productList);
    return null;
  }

  public static class ChangeProductStatusRequest {
    public final long productId;
    public final boolean boughtStatus;

    public ChangeProductStatusRequest(long product, boolean boughtStatus) {
      this.productId = product;
      this.boughtStatus = boughtStatus;
    }
  }
}
