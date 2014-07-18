package pl.charmas.shoppinglist.products.core.usecase;

import javax.inject.Inject;

import pl.charmas.shoppinglist.common.usecase.UseCase;
import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.ProductToAddBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;

public class AddProductUseCase implements UseCase<ProductBoundary, ProductToAddBoundary> {
    private final ProductsDataSource productsDataSource;

    @Inject
    public AddProductUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    @Override
    public ProductBoundary execute(final ProductToAddBoundary productToAddBoundary) {
        ProductGateway product = productsDataSource.createProduct(productToAddBoundary.getName(), productToAddBoundary.isBought());
        return new ProductBoundary(product.getId(), product.getName(), product.isBought());
    }
}
