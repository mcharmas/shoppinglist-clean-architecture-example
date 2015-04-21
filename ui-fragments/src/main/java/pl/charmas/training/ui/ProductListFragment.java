package pl.charmas.training.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import dagger.Module;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.presentation.ProductListPresenter;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.ui.base.PresenterListFragment;

public class ProductListFragment extends PresenterListFragment<ProductListPresenter.ProductListUI>
    implements ProductListPresenter.ProductListUI {

  @Inject ProductListPresenter presenter;

  private ProductListAdapter.OnProductStatusChangedListener onProductStatusChangedListener;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupPresenter(presenter, this);
    setHasOptionsMenu(true);
    onProductStatusChangedListener = new ProductListAdapter.OnProductStatusChangedListener() {
      @Override public void onProductStatusChanged(long productId, boolean isBought) {
        presenter.productStatusChanged(productId, isBought);
      }
    };
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setEmptyText("No products to buy...");
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
  }

  @Override public void showProductList(List<ProductViewModel> productViewModels) {
    ProductListAdapter adapter = (ProductListAdapter) getListAdapter();
    if (adapter != null) {
      adapter.swapData(productViewModels);
    } else {
      setListAdapter(new ProductListAdapter(getActivity(), productViewModels, onProductStatusChangedListener));
    }
    setListShown(true);
  }

  @Override public void showProgress() {
    setListShown(false);
  }

  @Override public void navigateToAddProduct() {
    ((ProductListFragmentActivity) getActivity()).navigateToAddProduct();
  }

  @Override public void preparePresenterModules(List<Object> modules) {
    super.preparePresenterModules(modules);
    modules.add(new PresentationModule());
  }

  public void onProductAdded() {
    presenter.onProductAdded();
  }

  @Module(injects = {
      ProductListPresenter.class,
      ProductListFragment.class
  }, complete = false) public static class PresentationModule {
  }
}
