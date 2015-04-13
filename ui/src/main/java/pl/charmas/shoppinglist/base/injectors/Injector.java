package pl.charmas.shoppinglist.base.injectors;

import dagger.ObjectGraph;

public interface Injector {
  void inject(Object target);

  ObjectGraph getObjectGraph();
}
