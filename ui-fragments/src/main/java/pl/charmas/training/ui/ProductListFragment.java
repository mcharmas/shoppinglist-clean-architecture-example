package pl.charmas.training.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import dagger.Component;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.presentation.ProductListPresenter;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.presentation.scope.PresenterScope;
import pl.charmas.shoppinglist.ui.base.PresenterListFragment;

public class ProductListFragment extends
    PresenterListFragment<ProductListPresenter.ProductListUI, ProductListFragment.ProductListComponent>
    implements ProductListPresenter.ProductListUI {

  @Inject ProductListPresenter presenter;

  private ProductListAdapter.OnProductStatusChangedListener onProductStatusChangedListener;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
    setupPresenter(presenter, this);
    setHasOptionsMenu(true);
    onProductStatusChangedListener = new ProductListAdapter.OnProductStatusChangedListener() {
      @Override public void onProductStatusChanged(long productId, boolean isBought) {
        presenter.productStatusChanged(productId, isBought);
      }
    };
  }

  @Override protected ProductListComponent onCreateComponent() {
    return DaggerProductListFragment_ProductListComponent.builder()
        .appComponent(((ProductListApp) getActivity().getApplication()).getComponent())
        .build();
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
      setListAdapter(
          new ProductListAdapter(getActivity(), productViewModels, onProductStatusChangedListener));
    }
    setListShown(true);
  }

  @Override public void showProgress() {
    setListShown(false);
  }

  @Override public void navigateToAddProduct() {
    ((ProductListFragmentActivity) getActivity()).navigateToAddProduct();
  }

  public void onProductAdded() {
    presenter.onProductAdded();
  }

  @PresenterScope
  @Component(dependencies = ProductListApp.AppComponent.class)
  public interface ProductListComponent {
    void inject(ProductListFragment target);
  }
}
