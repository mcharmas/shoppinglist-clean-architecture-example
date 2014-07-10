package pl.charmas.shoppinglist.products.core.gateway;

public class ProductGateway {
    private final Long id;
    private final String name;
    private final boolean isBought;

    public ProductGateway(Long id, String name, boolean isBought) {
        this.id = id;
        this.name = name;
        this.isBought = isBought;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBought() {
        return isBought;
    }
}
