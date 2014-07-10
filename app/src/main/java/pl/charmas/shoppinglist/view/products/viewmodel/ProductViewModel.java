package pl.charmas.shoppinglist.view.products.viewmodel;

import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;

public class ProductViewModel {
    public final long id;
    public final String name;
    public final boolean isBought;

    public ProductViewModel(long id, String name, boolean isBought) {
        this.id = id;
        this.name = name;
        this.isBought = isBought;
    }

    public static ProductViewModel createFrom(ProductBoundary productBoundary) {
        return new ProductViewModel(productBoundary.getId(), productBoundary.getName(), productBoundary.isBought());
    }
}
