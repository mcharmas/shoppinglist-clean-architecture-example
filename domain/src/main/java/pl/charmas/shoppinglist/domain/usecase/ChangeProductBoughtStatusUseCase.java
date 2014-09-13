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
        Product product = new Product(currentProduct.getId(), currentProduct.getName(), currentProduct.isBought());
        Product updatedProduct;
        if (request.boughStatus) {
            updatedProduct = product.markBought();
        } else {
            updatedProduct = product.markNotBought();
        }
        return productsDataSource.updateProduct(updatedProduct);
    }

    public static class ChangeProductStatusRequest {
        public final long productId;
        public final boolean boughStatus;

        public ChangeProductStatusRequest(long product, boolean boughStatus) {
            this.productId = product;
            this.boughStatus = boughStatus;
        }
    }
}
