package pl.charmas.shoppinglist.presenters;

import javax.inject.Inject;

import pl.charmas.shoppinglist.base.UILifecycleObserver;
import pl.charmas.shoppinglist.domain.usecase.AddProductUseCase;
import pl.charmas.shoppinglist.ui.AddProductUI;

public class AddProductPresenter implements AddProductUI.UICallbacks, UILifecycleObserver {
    private final AddProductUseCase addProductUseCase;
    private final AddProductUI ui;

    @Inject
    public AddProductPresenter(AddProductUseCase addProductUseCase, AddProductUI ui) {
        this.addProductUseCase = addProductUseCase;
        this.ui = ui;
        this.ui.setUICallbacks(this);
    }

    @Override
    public void onAddProductRequest(String productName) {
        addProductUseCase.execute(productName);
        ui.navigateBack();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
