package pl.charmas.shoppinglist.products.core.boundaries;

public class ProductRemovedBoundary {
    private final long removedProductId;

    public ProductRemovedBoundary(long removedProductId) {
        this.removedProductId = removedProductId;
    }

    public long getRemovedProductId() {
        return removedProductId;
    }
}
