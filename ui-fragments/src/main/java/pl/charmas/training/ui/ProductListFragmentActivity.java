package pl.charmas.training.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import pl.charmas.shoppinglist.ui.base.PresenterFragmentActivity;

public class ProductListFragmentActivity extends PresenterFragmentActivity {

  private static final String TAG_FRAGMENT_PRODUCT_LIST = "TAG_FRAGMENT_PRODUCT_LIST";

  private ProductListFragment productListFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment_container);
    if (savedInstanceState == null) {
      productListFragment = new ProductListFragment();
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment_container, productListFragment, TAG_FRAGMENT_PRODUCT_LIST)
          .commit();
    } else {
      productListFragment = (ProductListFragment) getSupportFragmentManager()
          .findFragmentByTag(TAG_FRAGMENT_PRODUCT_LIST);
    }
  }

  public void navigateToAddProduct() {
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment_container, new AddProductFragment())
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .addToBackStack(null)
        .commit();
  }

  public void onProductAdded() {
    getSupportFragmentManager().popBackStack();
    productListFragment.onProductAdded();
  }
}
