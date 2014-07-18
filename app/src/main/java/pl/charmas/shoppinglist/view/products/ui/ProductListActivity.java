package pl.charmas.shoppinglist.view.products.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.base.BaseListActivity;
import pl.charmas.shoppinglist.common.rx.AsyncUseCase;
import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.ProductListBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.StatusToChangeBoundary;
import pl.charmas.shoppinglist.products.core.usecase.ChangeProductBoughtStatusUseCase;
import pl.charmas.shoppinglist.products.core.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.products.core.usecase.RemoveAllBoughtProductsUseCase;
import pl.charmas.shoppinglist.view.products.viewmodel.ProductViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ProductListActivity extends BaseListActivity {
    @Inject
    ListProductsUseCase listProductsUseCase;

    @Inject
    ChangeProductBoughtStatusUseCase changeProductBoughtStatusUseCase;

    @Inject
    RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase;

    private final ProductListPresenter productListPresenter = new ProductListPresenter();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem addMenuItem = menu.add("Add");
        addMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        addMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ProductListActivity.this, AddProductActivity.class));
                return true;
            }
        });

        MenuItem removeAllBoughtItem = menu.add("Remove bought");
        removeAllBoughtItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                removeAllBoughtItems();
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshProductList();
    }

    private void removeAllBoughtItems() {
        removeAllBoughtProductsUseCase.execute();
        refreshProductList();
    }

    private void updateProductStatus(long productId, boolean isBought) {
        changeProductBoughtStatusUseCase.execute(new StatusToChangeBoundary(productId, isBought));
        refreshProductList();
    }

    private void refreshProductList() {
        AsyncUseCase.wrap(listProductsUseCase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ProductListBoundary>() {
                    @Override
                    public void call(ProductListBoundary productListBoundary) {
                        productListPresenter.present(listProductsUseCase.execute());
                    }
                });
    }

    private class ProductListPresenter {
        public void present(ProductListBoundary productListBoundary) {
            present(mapToViewModel(productListBoundary));
        }

        private ArrayList<ProductViewModel> mapToViewModel(ProductListBoundary productListBoundary) {
            ArrayList<ProductViewModel> productViewModels = new ArrayList<>();
            for (ProductBoundary productBoundary : productListBoundary.getProducts()) {
                productViewModels.add(ProductViewModel.createFrom(productBoundary));
            }
            return productViewModels;
        }

        private void present(List<ProductViewModel> productViewModels) {
            if (getListAdapter() != null) {
                ProductListAdapter adapter = (ProductListAdapter) getListAdapter();
                adapter.clear();
                adapter.addAll(productViewModels);
                adapter.notifyDataSetChanged();
            } else {
                setListAdapter(new ProductListAdapter(
                        ProductListActivity.this,
                        productViewModels,
                        new ProductListAdapter.OnProductStatusChangedListener() {
                            @Override
                            public void onProductStatusChanged(long productId, boolean isBought) {
                                updateProductStatus(productId, isBought);
                            }
                        }
                ));
            }
        }
    }
}
