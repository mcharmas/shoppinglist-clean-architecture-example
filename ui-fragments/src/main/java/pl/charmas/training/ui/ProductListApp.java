package pl.charmas.training.ui;

import android.app.Application;
import dagger.Component;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.ProductsDataSourceModule;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;

public class ProductListApp extends Application {

  private AppComponent component;

  @Override
  public void onCreate() {
    super.onCreate();
    initializeDagger();
  }

  private void initializeDagger() {
    component = DaggerProductListApp_AppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

  public AppComponent getComponent() {
    return component;
  }

  @Singleton
  @Component(modules = {
      AppModule.class,
      ProductsDataSourceModule.class
  })
  public interface AppComponent {
    ProductsDataSource getDataSource();

    AsyncUseCase getAsyncUseCase();
  }
}
