package pl.charmas.shoppinglist.products.core.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.common.usecase.UseCaseArgumentless;
import pl.charmas.shoppinglist.products.core.boundaries.ProductRemovedBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;

public class RemoveAllBoughtProductsUseCase implements UseCaseArgumentless<List<ProductRemovedBoundary>> {
    private final RemoveProductUseCase removeProductUseCase;
    private final ProductsDataSource productsDataSource;

    @Inject
    public RemoveAllBoughtProductsUseCase(RemoveProductUseCase removeProductUseCase, ProductsDataSource productsDataSource) {
        this.removeProductUseCase = removeProductUseCase;
        this.productsDataSource = productsDataSource;
    }

    public List<ProductRemovedBoundary> execute() {
        List<ProductGateway> allProducts = productsDataSource.listAll();
        List<ProductRemovedBoundary> removedProducts = new ArrayList<>();
        for (ProductGateway productGateway : allProducts) {
            if (productGateway.isBought()) {
                removedProducts.add(removeProductUseCase.execute(productGateway.getId()));
            }
        }

        return removedProducts;
    }
}
