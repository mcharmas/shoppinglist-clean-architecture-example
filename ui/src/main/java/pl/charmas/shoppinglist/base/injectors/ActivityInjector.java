package pl.charmas.shoppinglist.base.injectors;

import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.List;
import pl.charmas.shoppinglist.base.TestActivityModules;

public class ActivityInjector implements Injector {
  private final Object nonConfigurationInstance;
  private final ModuleFactory moduleFactory;
  private final Injector baseInjector;
  private ObjectGraph presenterGraph;

  public ActivityInjector(Object nonConfigurationInstance, ModuleFactory moduleFactory, Injector baseInjector) {
    this.nonConfigurationInstance = nonConfigurationInstance;
    this.moduleFactory = moduleFactory;
    this.baseInjector = baseInjector;
  }

  @Override public void inject(Object target) {
    getObjectGraph().inject(target);
  }

  @Override public ObjectGraph getObjectGraph() {
    ObjectGraph baseGraph = baseInjector.getObjectGraph();
    presenterGraph = (ObjectGraph) nonConfigurationInstance;
    if (presenterGraph == null) {
      Object[] presenterModules = getPresenterModules();
      if (presenterModules.length != 0) {
        presenterGraph = TestActivityModules.plusTestModules(baseGraph, presenterModules);
      }
    }

    ObjectGraph injectionGraph = presenterGraph == null ? baseGraph : presenterGraph;
    Object[] activityModules = getInstanceModules();
    if (activityModules.length != 0) {
      injectionGraph = injectionGraph.plus(activityModules);
    }
    return injectionGraph;
  }

  public Object getNonConfigurationInstance() {
    return presenterGraph;
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
}
