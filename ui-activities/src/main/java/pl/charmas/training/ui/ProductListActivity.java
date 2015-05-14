package pl.charmas.training.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import dagger.Component;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.presentation.ProductListPresenter;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.presentation.scope.PresenterScope;
import pl.charmas.shoppinglist.ui.base.PresenterListActivity;

public class ProductListActivity extends
    PresenterListActivity<ProductListPresenter.ProductListUI, ProductListActivity.ProductListActivityComponent>
    implements ProductListPresenter.ProductListUI {

  private static final int REQUEST_ADD_PRODUCT = 0;

  @Inject ProductListPresenter presenter;

  private ProductListAdapter.OnProductStatusChangedListener onProductStatusChangedListener;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setupPresenter(presenter, this);
    onProductStatusChangedListener = new ProductListAdapter.OnProductStatusChangedListener() {
      @Override public void onProductStatusChanged(long productId, boolean isBought) {
        presenter.productStatusChanged(productId, isBought);
      }
    };
  }

  @Override protected ProductListActivityComponent onCreateComponent() {
    ProductListApp.AppComponent appComponent = ((ProductListApp) getApplication()).getComponent();
    return DaggerProductListActivity_ProductListActivityComponent.builder()
        .appComponent(appComponent)
        .build();
  }

  @Override public void showProductList(List<ProductViewModel> productViewModels) {
    setProgressBarIndeterminateVisibility(false);
    ProductListAdapter adapter = (ProductListAdapter) getListAdapter();
    if (adapter != null) {
      adapter.swapData(productViewModels);
    } else {
      setListAdapter(
          new ProductListAdapter(this, productViewModels, onProductStatusChangedListener)
      );
    }
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
    setProgressBarIndeterminateVisibility(true);
  }

  @Override public void navigateToAddProduct() {
    startActivityForResult(new Intent(this, AddProductActivity.class), REQUEST_ADD_PRODUCT);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_ADD_PRODUCT && resultCode == Activity.RESULT_OK) {
      presenter.onProductAdded();
    }
  }

  @PresenterScope
  @Component(dependencies = ProductListApp.AppComponent.class)
  public interface ProductListActivityComponent {
    void inject(ProductListActivity target);
  }
}
