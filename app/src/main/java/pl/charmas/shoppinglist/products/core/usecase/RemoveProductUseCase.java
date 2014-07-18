package pl.charmas.shoppinglist.products.core.usecase;

import javax.inject.Inject;

import pl.charmas.shoppinglist.products.core.boundaries.ProductRemovedBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;

public class RemoveProductUseCase {
    private final ProductsDataSource productsDataSource;

    @Inject
    public RemoveProductUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    public ProductRemovedBoundary execute(final long productId) {
        productsDataSource.removeProduct(productId);
        return new ProductRemovedBoundary(productId);
    }
}
