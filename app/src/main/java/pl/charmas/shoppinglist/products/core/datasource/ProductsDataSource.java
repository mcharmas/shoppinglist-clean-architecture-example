package pl.charmas.shoppinglist.products.core.datasource;

import java.util.List;

import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;

public interface ProductsDataSource {
    ProductGateway createProduct(String name, boolean isBought);

    void removeProduct(long id);

    ProductGateway updateProduct(ProductGateway productToUpdate);

    List<ProductGateway> listAll();

    ProductGateway getProduct(long id);
}
