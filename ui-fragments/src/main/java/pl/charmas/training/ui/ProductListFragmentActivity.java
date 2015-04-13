package pl.charmas.training.ui;

import android.os.Bundle;
import pl.charmas.shoppinglist.ui.base.PresenterFragmentActivity;

public class ProductListFragmentActivity extends PresenterFragmentActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment_container);
    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment_container, new ProductListFragment())
          .commit();
    }
  }
}
