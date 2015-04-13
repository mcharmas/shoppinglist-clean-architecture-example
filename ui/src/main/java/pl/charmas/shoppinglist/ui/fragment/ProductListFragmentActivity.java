package pl.charmas.shoppinglist.ui.fragment;

import android.os.Bundle;
import android.view.Window;
import com.example.mcharmas.myapplication.R;
import pl.charmas.shoppinglist.base.PresenterFragmentActivity;

public class ProductListFragmentActivity extends PresenterFragmentActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_fragment_container);
    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment_container, new ProductListFragment())
          .commit();
    }
  }
}
