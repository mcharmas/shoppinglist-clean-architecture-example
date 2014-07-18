package pl.charmas.shoppinglist.products.core.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.common.usecase.UseCaseArgumentless;
import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.ProductListBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;

public class ListProductsUseCase implements UseCaseArgumentless<ProductListBoundary> {
    private final ProductsDataSource productsDataSource;

    @Inject
    public ListProductsUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    @Override
    public ProductListBoundary execute() {
        List<ProductGateway> allProducts = productsDataSource.listAll();
        List<ProductBoundary> boundaries = new ArrayList<>();
        for (ProductGateway productGateway : allProducts) {
            boundaries.add(new ProductBoundary(productGateway.getId(), productGateway.getName(), productGateway.isBought()));
        }
        return new ProductListBoundary(boundaries);
    }
}
