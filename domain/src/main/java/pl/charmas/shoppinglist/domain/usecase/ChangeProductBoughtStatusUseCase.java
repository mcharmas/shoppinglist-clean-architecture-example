package pl.charmas.shoppinglist.domain.usecase;

import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;

public class ChangeProductBoughtStatusUseCase implements UseCase<Product, ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest> {
  private final ProductsDataSource productsDataSource;

  @Inject
  public ChangeProductBoughtStatusUseCase(ProductsDataSource productsDataSource) {
    this.productsDataSource = productsDataSource;
  }

  @Override
  public Product execute(final ChangeProductStatusRequest request) {
    Product currentProduct = productsDataSource.getProduct(request.productId);
    Product updatedProduct;
    if (request.boughtStatus) {
      updatedProduct = currentProduct.markBought();
    } else {
      updatedProduct = currentProduct.markNotBought();
    }
    return productsDataSource.updateProduct(updatedProduct);
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
