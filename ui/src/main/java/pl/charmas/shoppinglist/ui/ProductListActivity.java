package pl.charmas.shoppinglist.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import dagger.Module;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.base.BasePresenterListActivity;
import pl.charmas.shoppinglist.presentation.ProductListPresenter;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;

public class ProductListActivity extends BasePresenterListActivity<ProductListPresenter.ProductListUI>
    implements ProductListPresenter.ProductListUI {

  @Inject ProductListPresenter presenter;

  private ProductListAdapter.OnProductStatusChangedListener onProductStatusChangedListener;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupPresenter(presenter, this);
    onProductStatusChangedListener = new ProductListAdapter.OnProductStatusChangedListener() {
      @Override public void onProductStatusChanged(long productId, boolean isBought) {
        presenter.productStatusChanged(productId, isBought);
      }
    };
  }

  @Override public void showProductList(List<ProductViewModel> productViewModels) {
    setListAdapter(new ProductListAdapter(this, productViewModels, onProductStatusChangedListener));
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuItem addMenuItem = menu.add("Add");
    addMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    addMenuItem.setOnMenuItemClickListener(
        new MenuItem.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            presenter.onAddNewProduct();
            return true;
          }
        }
    );

    MenuItem removeAllBoughtItem = menu.add("Remove bought");
    removeAllBoughtItem.setOnMenuItemClickListener(
        new MenuItem.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            presenter.onRemoveBoughtProducts();
            return true;
          }
        }
    );
    return true;
  }

  @Override public void showProgress() {
    setListAdapter(null);
  }

  @Override public void prepareAdditionalPresenterModules(List<Object> modules) {
    super.prepareAdditionalPresenterModules(modules);
    modules.add(new PresentationModule());
  }

  @Module(injects = {
      ProductListPresenter.class,
      ProductListActivity.class
  }, complete = false) public class PresentationModule {
  }
}
