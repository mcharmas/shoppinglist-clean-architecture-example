package pl.charmas.shoppinglist.presenters;

import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.base.UILifecycleObserver;
import pl.charmas.shoppinglist.common.rx.AsyncUseCase;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.usecase.ChangeProductBoughtStatusUseCase;
import pl.charmas.shoppinglist.domain.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.domain.usecase.RemoveAllBoughtProductsUseCase;
import pl.charmas.shoppinglist.model.mappers.ProductToViewModelMapper;
import pl.charmas.shoppinglist.ui.ProductListUI;

import static pl.charmas.shoppinglist.domain.usecase.ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest;

public class ProductListPresenter implements UILifecycleObserver, ProductListUI.UICallbacks, Presenter<ProductListUI> {
    private final ProductToViewModelMapper mapper;
    private final ListProductsUseCase listProductsUseCase;
    private final RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase;
    private final ChangeProductBoughtStatusUseCase changeProductBoughtStatusUseCase;
    private ProductListUI ui;

    @Inject
    public ProductListPresenter(ListProductsUseCase listProductsUseCase, ProductToViewModelMapper mapper, RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase, ChangeProductBoughtStatusUseCase changeProductBoughtStatusUseCase) {
        this.listProductsUseCase = listProductsUseCase;
        this.mapper = mapper;
        this.removeAllBoughtProductsUseCase = removeAllBoughtProductsUseCase;
        this.changeProductBoughtStatusUseCase = changeProductBoughtStatusUseCase;
    }

    @Override
    public void initialize(ProductListUI productListUI) {
        this.ui = productListUI;
        this.ui.setUICallbacks(this);
    }

    @Override
    public void onStart() {
        ui.showProgress();
        fetchProductList();
    }

    private void fetchProductList() {
        AsyncUseCase.callback(listProductsUseCase).register(new AsyncUseCase.AsyncCallback<List<Product>>() {
            @Override
            public void onResultOk(List<Product> products) {
                ui.showProductList(mapper.toViewModel(products));
            }

            @Override
            public void onError(Throwable throwable) {
                presentError(throwable);
            }
        });
    }

    @Override
    public void onStop() {

    }

    private void presentError(Throwable throwable) {
        // TODO: error handling
    }

    @Override
    public void onRemoveBoughtProducts() {
        AsyncUseCase.callback(removeAllBoughtProductsUseCase).register(new AsyncUseCase.AsyncCallback<Integer>() {
            @Override
            public void onResultOk(Integer result) {
                fetchProductList();
            }

            @Override
            public void onError(Throwable throwable) {
                presentError(throwable);
            }
        });
    }

    @Override
    public void onAddNewProduct() {
        ui.navigateToAddNewProduct();
    }

    @Override
    public void onProductStatusChanged(long id, boolean isBought) {
        AsyncUseCase.callback(changeProductBoughtStatusUseCase, new ChangeProductStatusRequest(id, isBought))
                .register(new AsyncUseCase.AsyncCallback<Product>() {
                    @Override
                    public void onResultOk(Product result) {
                        fetchProductList();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        presentError(throwable);
                    }
                });
    }

}
