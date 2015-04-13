package pl.charmas.shoppinglist.base.injectors;

import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.List;
import pl.charmas.shoppinglist.app.ProductListApp;
import pl.charmas.shoppinglist.base.BasePresenterFragmentActivity;

public class FragmentInjector {
  private final int id;
  private final BasePresenterFragmentActivity activity;
  private final ModuleFactory moduleFactory;

  public FragmentInjector(int id, BasePresenterFragmentActivity activity, ModuleFactory moduleFactory) {
    this.id = id;
    this.activity = activity;
    this.moduleFactory = moduleFactory;
  }

  public void inject(Object target) {
    ObjectGraph appGraph = ProductListApp.get(activity).graph();
    ObjectGraph presenterGraph = activity.getPresenterGraphForFragment(id);
    if (presenterGraph == null) {
      Object[] presenterModules = getPresenterModules();
      if (presenterModules.length != 0) {
        presenterGraph = appGraph.plus(presenterModules);
        activity.setPresenterGraphForFragment(id, presenterGraph);
      }
    }

    ObjectGraph injectionGraph = presenterGraph == null ? appGraph : presenterGraph;
    Object[] activityModules = getInstanceModules();
    if (activityModules.length != 0) {
      injectionGraph = injectionGraph.plus(activityModules);
    }

    injectionGraph.inject(target);
  }

  private Object[] getInstanceModules() {
    List<Object> modules = new ArrayList<>();
    moduleFactory.prepareInstanceModules(modules);
    return modules.toArray(new Object[modules.size()]);
  }

  private Object[] getPresenterModules() {
    List<Object> modules = new ArrayList<>();
    moduleFactory.preparePresenterModules(modules);
    return modules.toArray(new Object[modules.size()]);
  }

  public void destroyGraph() {
    activity.clearPresenterGraphForFragment(id);
  }
}
