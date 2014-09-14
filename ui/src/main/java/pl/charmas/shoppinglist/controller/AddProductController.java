package pl.charmas.shoppinglist.controller;

import javax.inject.Inject;

import pl.charmas.shoppinglist.common.rx.AsyncUseCase;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.usecase.AddProductUseCase;
import pl.charmas.shoppinglist.ui.AddProductUI;

public class AddProductController implements AddProductUI.UICallbacks, Controller<AddProductUI> {
    private final AddProductUseCase addProductUseCase;
    private AddProductUI ui;

    @Inject
    public AddProductController(AddProductUseCase addProductUseCase) {
        this.addProductUseCase = addProductUseCase;
    }

    @Override
    public void onAddProductRequest(String productName) {
        AsyncUseCase.callback(addProductUseCase, productName).register(new AsyncUseCase.AsyncCallback<Product>() {
            @Override
            public void onResultOk(Product result) {
                ui.navigateBack();
            }

            @Override
            public void onError(Throwable throwable) {
                // TODO: error handling
            }
        });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void initialize(AddProductUI addProductUI) {
        this.ui = addProductUI;
        this.ui.setUICallbacks(this);
    }
}
