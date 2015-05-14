package pl.charmas.shoppinglist.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import java.util.HashMap;
import java.util.Map;

public class PresenterFragmentActivity extends ActionBarActivity {
  private Map<String, Object> fragmentPresenterGraphs;

  @Override protected void onCreate(Bundle savedInstanceState) {
    //noinspection unchecked
    fragmentPresenterGraphs = (Map<String, Object>) getLastCustomNonConfigurationInstance();
    if (fragmentPresenterGraphs == null) {
      fragmentPresenterGraphs = new HashMap<>();
    }
    super.onCreate(savedInstanceState);
  }

  @Override public Object onRetainCustomNonConfigurationInstance() {
    return fragmentPresenterGraphs;
  }

  public Object getComponentForFragment(String id) {
    return fragmentPresenterGraphs.get(id);
  }

  public void clearComponentForFragment(String id) {
    fragmentPresenterGraphs.remove(id);
  }

  public void setComponentForFragment(String id, Object presenterGraph) {
    fragmentPresenterGraphs.put(id, presenterGraph);
  }
}
