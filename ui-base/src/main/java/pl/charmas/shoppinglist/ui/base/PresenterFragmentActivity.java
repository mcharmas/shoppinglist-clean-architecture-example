package pl.charmas.shoppinglist.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import dagger.ObjectGraph;
import pl.charmas.shoppinglist.ui.base.injectors.Injector;

public class PresenterFragmentActivity extends ActionBarActivity {
  private SparseArray<ObjectGraph> fragmentPresenterGraphs;

  @Override protected void onCreate(Bundle savedInstanceState) {
    //noinspection unchecked
    fragmentPresenterGraphs = (SparseArray<ObjectGraph>) getLastCustomNonConfigurationInstance();
    if (fragmentPresenterGraphs == null) {
      fragmentPresenterGraphs = new SparseArray<>();
    }
    super.onCreate(savedInstanceState);
  }

  @Override public Object onRetainCustomNonConfigurationInstance() {
    return fragmentPresenterGraphs;
  }

  public ObjectGraph getPresenterGraphForFragment(int id) {
    return fragmentPresenterGraphs.get(id);
  }

  public void clearPresenterGraphForFragment(int id) {
    fragmentPresenterGraphs.remove(id);
  }

  public void setPresenterGraphForFragment(int id, ObjectGraph presenterGraph) {
    fragmentPresenterGraphs.append(id, presenterGraph);
  }

  public Injector getBaseInjector() {
    return (Injector) getApplication();
  }
}
