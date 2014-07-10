package pl.charmas.shoppinglist.products.core.boundaries;

import java.util.List;

public class ProductListBoundary {
    private final List<ProductBoundary> products;

    public ProductListBoundary(List<ProductBoundary> products) {
        this.products = products;
    }

    public List<ProductBoundary> getProducts() {
        return products;
    }
}
