package pl.charmas.shoppinglist.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import dagger.ObjectGraph;
import java.util.HashMap;
import java.util.Map;
import pl.charmas.shoppinglist.ui.base.injectors.Injector;

public class PresenterFragmentActivity extends ActionBarActivity {
  private Map<String, ObjectGraph> fragmentPresenterGraphs;

  @Override protected void onCreate(Bundle savedInstanceState) {
    //noinspection unchecked
    fragmentPresenterGraphs = (Map<String, ObjectGraph>) getLastCustomNonConfigurationInstance();
    if (fragmentPresenterGraphs == null) {
      fragmentPresenterGraphs = new HashMap<>();
    }
    super.onCreate(savedInstanceState);
  }

  @Override public Object onRetainCustomNonConfigurationInstance() {
    return fragmentPresenterGraphs;
  }

  public ObjectGraph getPresenterGraphForFragment(String id) {
    return fragmentPresenterGraphs.get(id);
  }

  public void clearPresenterGraphForFragment(String id) {
    fragmentPresenterGraphs.remove(id);
  }

  public void setPresenterGraphForFragment(String id, ObjectGraph presenterGraph) {
    fragmentPresenterGraphs.put(id, presenterGraph);
  }

  public Injector getBaseInjector() {
    return (Injector) getApplication();
  }
}
