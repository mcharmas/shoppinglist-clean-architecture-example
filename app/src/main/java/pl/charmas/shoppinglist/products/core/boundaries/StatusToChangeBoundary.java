package pl.charmas.shoppinglist.products.core.boundaries;

public class StatusToChangeBoundary {
    private final long id;
    private final boolean status;

    public StatusToChangeBoundary(long id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public boolean isBought() {
        return status;
    }
}
