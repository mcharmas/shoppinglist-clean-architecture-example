package pl.charmas.shoppinglist.domain.usecase;

import javax.inject.Inject;

import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;

public class AddProductUseCase implements UseCase<Product, String> {
    private final ProductsDataSource productsDataSource;

    @Inject
    public AddProductUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    @Override
    public Product execute(final String productName) {
        return productsDataSource.createProduct(productName, false);
    }
}
