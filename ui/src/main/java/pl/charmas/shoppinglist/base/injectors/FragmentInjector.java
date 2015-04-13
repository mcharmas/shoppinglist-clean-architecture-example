package pl.charmas.shoppinglist.base.injectors;

import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.List;
import pl.charmas.shoppinglist.base.PresenterFragmentActivity;

public class FragmentInjector implements Injector {
  private final int id;
  private final PresenterFragmentActivity activity;
  private final ModuleFactory moduleFactory;
  private final Injector baseInjector;

  public FragmentInjector(int id, PresenterFragmentActivity activity, ModuleFactory moduleFactory) {
    this.id = id;
    this.activity = activity;
    this.moduleFactory = moduleFactory;
    this.baseInjector = activity.getBaseInjector();
  }

  public void inject(Object target) {
    getObjectGraph().inject(target);
  }

  @Override public ObjectGraph getObjectGraph() {
    ObjectGraph baseGraph = baseInjector.getObjectGraph();
    ObjectGraph presenterGraph = activity.getPresenterGraphForFragment(id);
    if (presenterGraph == null) {
      Object[] presenterModules = getPresenterModules();
      if (presenterModules.length != 0) {
        presenterGraph = baseGraph.plus(presenterModules);
        activity.setPresenterGraphForFragment(id, presenterGraph);
      }
    }

    ObjectGraph injectionGraph = presenterGraph == null ? baseGraph : presenterGraph;
    Object[] activityModules = getInstanceModules();
    if (activityModules.length != 0) {
      injectionGraph = injectionGraph.plus(activityModules);
    }
    return injectionGraph;
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
