package pl.charmas.shoppinglist.app;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;

public class ProductListApp extends Application {

  private ObjectGraph objectGraph;

  public static ProductListApp get(Context context) {
    return (ProductListApp) context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    initializeDagger();
  }

  private void initializeDagger() {
    objectGraph = ObjectGraph.create(new AppModule());
  }

  public void inject(Object target) {
    objectGraph.inject(target);
  }

  public ObjectGraph graph() {
    return objectGraph;
  }
}
