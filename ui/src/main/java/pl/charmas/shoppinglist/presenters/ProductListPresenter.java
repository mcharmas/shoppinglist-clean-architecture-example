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
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static pl.charmas.shoppinglist.domain.usecase.ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest;

public class ProductListPresenter implements UILifecycleObserver, ProductListUI.UICallbacks {
    private final ProductListUI ui;
    private final ProductToViewModelMapper mapper;
    private final ListProductsUseCase listProductsUseCase;
    private final RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase;
    private final ChangeProductBoughtStatusUseCase changeProductBoughtStatusUseCase;

    @Inject
    public ProductListPresenter(ProductListUI ui, ListProductsUseCase listProductsUseCase, ProductToViewModelMapper mapper, RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase, ChangeProductBoughtStatusUseCase changeProductBoughtStatusUseCase) {
        this.ui = ui;
        this.listProductsUseCase = listProductsUseCase;
        this.mapper = mapper;
        this.removeAllBoughtProductsUseCase = removeAllBoughtProductsUseCase;
        this.changeProductBoughtStatusUseCase = changeProductBoughtStatusUseCase;
        this.ui.setUICallbacks(this);
    }

    @Override
    public void onStart() {
        ui.showProgress();
        fetchProductList();
    }

    private void fetchProductList() {
        AsyncUseCase
                .wrap(listProductsUseCase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Product>>() {
                    @Override
                    public void call(List<Product> products) {
                        ui.hideProgress();
                        ui.showProductList(mapper.toViewModel(products));
                    }
                });
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRemoveBoughtProducts() {
        AsyncUseCase.wrap(removeAllBoughtProductsUseCase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        fetchProductList();
                    }
                });
    }

    @Override
    public void onAddNewProduct() {
        ui.navigateToAddNewProduct();
    }

    @Override
    public void onProductStatusChanged(long id, boolean isBought) {
        AsyncUseCase.wrap(changeProductBoughtStatusUseCase, new ChangeProductStatusRequest(id, isBought))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        fetchProductList();
                    }
                });
    }
}
