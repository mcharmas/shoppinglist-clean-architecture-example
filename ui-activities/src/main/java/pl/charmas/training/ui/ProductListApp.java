package pl.charmas.training.ui;

import android.app.Application;
import dagger.Component;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.ProductsDataSourceModule;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;

public class ProductListApp extends Application {

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    initializeDagger();
  }

  private void initializeDagger() {
    appComponent = DaggerProductListApp_AppComponent.builder()
        .appModule(new AppModule(this))
        .build();
    appComponent.inject(this);
  }

  public AppComponent getComponent() {
    return appComponent;
  }

  @Singleton
  @Component(modules = {
      AppModule.class,
      ProductsDataSourceModule.class
  })
  public interface AppComponent {
    void inject(ProductListApp app);

    ProductsDataSource getDataSource();

    AsyncUseCase getAsyncUseCase();
  }
}
