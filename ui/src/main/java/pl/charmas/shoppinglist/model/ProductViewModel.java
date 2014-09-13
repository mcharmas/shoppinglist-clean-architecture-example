package pl.charmas.shoppinglist.model;

public class ProductViewModel {
    private final long id;
    private final String name;
    private final boolean isBought;

    public ProductViewModel(long id, String name, boolean isBought) {
        this.id = id;
        this.name = name;
        this.isBought = isBought;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBought() {
        return isBought;
    }
}
