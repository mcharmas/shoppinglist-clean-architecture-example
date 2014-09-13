package pl.charmas.shoppinglist.ui;

public interface AddProductUI {
    public void setUICallbacks(UICallbacks callbacks);

    void navigateBack();

    public interface UICallbacks {
        void onAddProductRequest(String productName);
    }
}
