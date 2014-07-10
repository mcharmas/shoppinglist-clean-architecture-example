package pl.charmas.shoppinglist.products.datasource;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;

public class ProductsDataSourceImpl implements ProductsDataSource {
    private final Map<Long, ProductGateway> products = new HashMap<>();
    private long currentId = 0;

    @Inject
    public ProductsDataSourceImpl() {
        for (int i = 0; i < 20; i++) {
            createProduct("Product " + i, false);
        }
    }

    @Override
    public ProductGateway createProduct(String name, boolean isBought) {
        ProductGateway productGateway = new ProductGateway(currentId, name, isBought);
        currentId++;
        products.put(productGateway.getId(), productGateway);
        return productGateway;
    }

    @Override
    public void removeProduct(long id) {
        products.remove(id);
    }

    @Override
    public ProductGateway updateProduct(ProductGateway productToUpdate) {
        products.remove(productToUpdate.getId());
        products.put(productToUpdate.getId(), productToUpdate);
        return productToUpdate;
    }

    @Override
    public List<ProductGateway> listAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public ProductGateway getProduct(long id) {
        return products.get(id);
    }
}
