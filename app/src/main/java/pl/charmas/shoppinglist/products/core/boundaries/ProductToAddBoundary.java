package pl.charmas.shoppinglist.products.core.boundaries;

public class ProductToAddBoundary {
    private final String name;
    private final boolean isBought;

    public ProductToAddBoundary(String name, boolean isBought) {
        this.name = name;
        this.isBought = isBought;
    }

    public String getName() {
        return name;
    }

    public boolean isBought() {
        return isBought;
    }
}
