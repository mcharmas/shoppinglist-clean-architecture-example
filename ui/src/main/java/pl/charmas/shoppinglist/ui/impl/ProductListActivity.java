package pl.charmas.shoppinglist.ui.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pl.charmas.shoppinglist.app.AppModule;
import pl.charmas.shoppinglist.base.BaseListActivity;
import pl.charmas.shoppinglist.model.ProductViewModel;
import pl.charmas.shoppinglist.presenters.ProductListPresenter;
import pl.charmas.shoppinglist.ui.ProductListUI;

public class ProductListActivity extends BaseListActivity implements ProductListUI {

    @Inject
    ProductListPresenter productListPresenter;
    private OnProductStatusChangedListener onProductStatusChangedListener;
    private UICallbacks uiCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerUILifecycleObserver(productListPresenter);
        onProductStatusChangedListener = new OnProductStatusChangedListener();
    }

    @Override
    protected Object createActivityScopedModule() {
        return new ProductListActivityModule(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem addMenuItem = menu.add("Add");
        addMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        addMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                uiCallbacks.onAddNewProduct();
                return true;
            }
        });

        MenuItem removeAllBoughtItem = menu.add("Remove bought");
        removeAllBoughtItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                uiCallbacks.onRemoveBoughtProducts();
                return true;
            }
        });
        return true;
    }


    @Override
    public void showProductList(List<ProductViewModel> productViewModels) {
        setListAdapter(new ProductListAdapter(this, productViewModels, onProductStatusChangedListener));
    }

    @Override
    public void showProgress() {
        setListAdapter(null);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setUICallbacks(UICallbacks uiCallbacks) {
        this.uiCallbacks = uiCallbacks;
    }

    @Override
    public void navigateToAddNewProduct() {
        startActivity(new Intent(ProductListActivity.this, AddProductActivity.class));
    }

    @Module(injects = {ProductListActivity.class}, addsTo = AppModule.class)
    static class ProductListActivityModule {
        private final ProductListActivity productListActivity;

        ProductListActivityModule(ProductListActivity productListActivity) {
            this.productListActivity = productListActivity;
        }

        @Provides
        ProductListUI provideProductListUI() {
            return this.productListActivity;
        }
    }

    private class OnProductStatusChangedListener implements ProductListAdapter.OnProductStatusChangedListener {
        @Override
        public void onProductStatusChanged(long productId, boolean isBought) {
            uiCallbacks.onProductStatusChanged(productId, isBought);
        }
    }
}
