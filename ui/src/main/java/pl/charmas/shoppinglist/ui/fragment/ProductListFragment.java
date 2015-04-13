package pl.charmas.shoppinglist.ui.fragment;

import android.os.Bundle;
import dagger.Module;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.base.BasePresenterListFragment;
import pl.charmas.shoppinglist.presentation.ProductListPresenter;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.ui.ProductListAdapter;

public class ProductListFragment extends BasePresenterListFragment<ProductListPresenter.ProductListUI>
    implements ProductListPresenter.ProductListUI {

  @Inject ProductListPresenter presenter;
  private ProductListAdapter.OnProductStatusChangedListener onProductStatusChangedListener;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupPresenter(presenter, this);
    onProductStatusChangedListener = new ProductListAdapter.OnProductStatusChangedListener() {
      @Override public void onProductStatusChanged(long productId, boolean isBought) {
        presenter.productStatusChanged(productId, isBought);
      }
    };
  }

  @Override public void showProductList(List<ProductViewModel> productViewModels) {
    getActivity().setProgressBarIndeterminateVisibility(false);
    ProductListAdapter adapter = (ProductListAdapter) getListAdapter();
    if (adapter != null) {
      adapter.swapData(productViewModels);
    } else {
      setListAdapter(new ProductListAdapter(getActivity(), productViewModels, onProductStatusChangedListener));
    }
  }

  @Override public void showProgress() {
    setListAdapter(null);
    getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void navigateToAddProduct() {
    //TODO: navigate
  }

  @Override public void preparePresenterModules(List<Object> modules) {
    super.preparePresenterModules(modules);
    modules.add(new PresentationModule());
  }

  @Module(injects = {
      ProductListPresenter.class,
      ProductListFragment.class
  }, complete = false) public static class PresentationModule {
  }
}
