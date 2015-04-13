package pl.charmas.shoppinglist.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import dagger.ObjectGraph;

public class BasePresenterFragmentActivity extends FragmentActivity {
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
}
