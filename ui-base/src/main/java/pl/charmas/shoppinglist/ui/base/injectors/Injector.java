package pl.charmas.shoppinglist.ui.base.injectors;

import dagger.ObjectGraph;

public interface Injector {
  void inject(Object target);

  ObjectGraph getObjectGraph();
}
