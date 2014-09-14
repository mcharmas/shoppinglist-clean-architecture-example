package pl.charmas.shoppinglist.ui;

import java.util.List;

import pl.charmas.shoppinglist.model.ProductViewModel;

public interface ProductListUI {
    void showProductList(List<ProductViewModel> productViewModels);

    void showProgress();

    void setUICallbacks(UICallbacks uiCallbacks);

    void navigateToAddNewProduct();

    public interface UICallbacks {
        void onRemoveBoughtProducts();

        void onAddNewProduct();

        void onProductStatusChanged(long id, boolean isBought);
    }
}
