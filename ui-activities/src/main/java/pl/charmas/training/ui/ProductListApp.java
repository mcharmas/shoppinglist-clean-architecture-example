package pl.charmas.training.ui;

import android.app.Application;
import dagger.ObjectGraph;
import pl.charmas.shoppinglist.ui.base.injectors.Injector;

public class ProductListApp extends Application implements Injector {
  private ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();
    initializeDagger();
  }

  private void initializeDagger() {
    objectGraph = ObjectGraph.create(new AppModule());
  }

  @Override public void inject(Object target) {
    objectGraph.inject(target);
  }

  @Override public ObjectGraph getObjectGraph() {
    return objectGraph;
  }
}
