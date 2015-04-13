package pl.charmas.shoppinglist.base.injectors;

import android.content.Context;
import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.List;
import pl.charmas.shoppinglist.app.ProductListApp;
import pl.charmas.shoppinglist.base.TestActivityModules;

public class ActivityInjector {
  private final Context context;
  private final Object nonConfigurationInstance;
  private final ModuleFactory moduleFactory;
  private ObjectGraph presenterGraph;

  public ActivityInjector(Context context, Object nonConfigurationInstance, ModuleFactory moduleFactory) {
    this.context = context;
    this.nonConfigurationInstance = nonConfigurationInstance;
    this.moduleFactory = moduleFactory;
  }

  public void performInjection(Object target) {
    ObjectGraph appGraph = ProductListApp.get(context).graph();
    presenterGraph = (ObjectGraph) nonConfigurationInstance;
    if (presenterGraph == null) {
      Object[] presenterModules = getPresenterModules();
      if (presenterModules.length != 0) {
        presenterGraph = TestActivityModules.plusTestModules(appGraph, presenterModules);
      }
    }

    ObjectGraph injectionGraph = presenterGraph == null ? appGraph : presenterGraph;
    Object[] activityModules = getInstanceModules();
    if (activityModules.length != 0) {
      injectionGraph = injectionGraph.plus(activityModules);
    }

    injectionGraph.inject(target);
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
