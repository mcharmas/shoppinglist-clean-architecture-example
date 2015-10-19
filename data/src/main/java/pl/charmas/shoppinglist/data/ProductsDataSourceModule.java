package pl.charmas.shoppinglist.data;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.store.SharedPreferencesProductsDataSource;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;

@Module(complete = false, library = true)
public class ProductsDataSourceModule {

  @Singleton
  @Provides ProductsDataSource provideEntityStore(SharedPreferencesProductsDataSource store) {
    return store;
  }
}
