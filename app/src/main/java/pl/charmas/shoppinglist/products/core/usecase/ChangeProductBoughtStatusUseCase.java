package pl.charmas.shoppinglist.products.core.usecase;


import javax.inject.Inject;

import pl.charmas.shoppinglist.common.usecase.UseCase;
import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.StatusToChangeBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.entities.Product;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;

public class ChangeProductBoughtStatusUseCase implements UseCase<ProductBoundary, StatusToChangeBoundary> {
    private final ProductsDataSource productsDataSource;

    @Inject
    public ChangeProductBoughtStatusUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    @Override
    public ProductBoundary execute(final StatusToChangeBoundary statusToChangeBoundary) {
        ProductGateway currentProduct = productsDataSource.getProduct(statusToChangeBoundary.getId());
        Product product = new Product(currentProduct.getId(), currentProduct.getName(), currentProduct.isBought());
        Product updatedProduct;
        if (statusToChangeBoundary.isBought()) {
            updatedProduct = product.markBought();
        } else {
            updatedProduct = product.markNotBought();
        }
        productsDataSource.updateProduct(new ProductGateway(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.isBought()));
        return new ProductBoundary(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.isBought());
    }
}
