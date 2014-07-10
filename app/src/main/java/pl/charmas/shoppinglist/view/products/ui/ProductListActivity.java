package pl.charmas.shoppinglist.view.products.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.base.BaseListActivity;
import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.ProductListBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.ProductRemovedBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.StatusToChangeBoundary;
import pl.charmas.shoppinglist.products.core.usecase.ChangeProductBoughtStatusUseCase;
import pl.charmas.shoppinglist.products.core.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.products.core.usecase.RemoveAllBoughtProductsUseCase;
import pl.charmas.shoppinglist.view.products.viewmodel.ProductViewModel;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class ProductListActivity extends BaseListActivity {
    @Inject
    ListProductsUseCase listProductsUseCase;

    @Inject
    ChangeProductBoughtStatusUseCase changeProductBoughtStatusUseCase;

    @Inject
    RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase;

    private Subscription productListSubscription;

    @Override
    protected void onStart() {
        super.onStart();
        refreshProductList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unsubscribeFromProductList();
    }

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

    private void removeAllBoughtItems() {
        removeAllBoughtProductsUseCase
                .execute()
                .subscribe(new Action1<List<ProductRemovedBoundary>>() {
                    @Override
                    public void call(List<ProductRemovedBoundary> productRemovedBoundaries) {
                        refreshProductList();

                    }
                });
    }

    private void updateProductStatus(long productId, boolean isBought) {
        changeProductBoughtStatusUseCase
                .execute(new StatusToChangeBoundary(productId, isBought))
                .subscribe(new Action1<ProductBoundary>() {
                    @Override
                    public void call(ProductBoundary productBoundary) {
                        refreshProductList();
                    }
                });
    }

    private void refreshProductList() {
        unsubscribeFromProductList();
        productListSubscription = listProductsUseCase.execute()
                .map(new MapProductListBoundaryToViewModel())
                .subscribe(new PresentProductListAction());
    }

    private void unsubscribeFromProductList() {
        if (productListSubscription != null) {
            productListSubscription.unsubscribe();
            productListSubscription = null;
        }
    }

    private class PresentProductListAction implements Action1<List<ProductViewModel>> {
        @Override
        public void call(List<ProductViewModel> productViewModels) {
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

    private static class MapProductListBoundaryToViewModel implements Func1<ProductListBoundary, List<ProductViewModel>> {
        @Override
        public List<ProductViewModel> call(ProductListBoundary productListBoundary) {
            ArrayList<ProductViewModel> productViewModels = new ArrayList<>();
            for (ProductBoundary productBoundary : productListBoundary.getProducts()) {
                productViewModels.add(ProductViewModel.createFrom(productBoundary));
            }
            return productViewModels;
        }
    }
}
