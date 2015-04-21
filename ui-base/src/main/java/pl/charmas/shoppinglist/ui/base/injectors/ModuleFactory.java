package pl.charmas.shoppinglist.ui.base.injectors;

import java.util.List;

public interface ModuleFactory {
  void prepareInstanceModules(List<Object> modules);

  void preparePresenterModules(List<Object> modules);
}
