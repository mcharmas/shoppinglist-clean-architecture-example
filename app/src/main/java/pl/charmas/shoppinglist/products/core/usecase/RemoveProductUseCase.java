package pl.charmas.shoppinglist.products.core.usecase;

import javax.inject.Inject;

import pl.charmas.shoppinglist.common.usecase.UseCase;
import pl.charmas.shoppinglist.products.core.boundaries.ProductRemovedBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;

public class RemoveProductUseCase implements UseCase<ProductRemovedBoundary, Long> {
    private final ProductsDataSource productsDataSource;

    @Inject
    public RemoveProductUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    @Override
    public ProductRemovedBoundary execute(final Long productId) {
        productsDataSource.removeProduct(productId);
        return new ProductRemovedBoundary(productId);
    }
}
