package pl.charmas.shoppinglist.products.core.entities;

public class Product {
    private final long id;
    private final String name;
    private final boolean isBought;

    public Product(long id, String name, boolean isBought) {
        this.id = id;
        this.name = name;
        this.isBought = isBought;
    }

    public long getId() {
        return id;
    }

    public Product markBought() {
        return new Product(id, name, true);
    }

    public Product markNotBought() {
        return new Product(id, name, false);
    }

    public String getName() {
        return name;
    }

    public boolean isBought() {
        return isBought;
    }
}
