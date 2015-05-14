package pl.charmas.shoppinglist.data;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.datasource.ProductsDataSourceImpl;
import pl.charmas.shoppinglist.data.entity.mappers.ProductEntityMapper;
import pl.charmas.shoppinglist.data.store.ProductEntityStore;
import pl.charmas.shoppinglist.data.store.SharedPreferencesProductEntityStore;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;

@Module
public class ProductsDataSourceModule {

  @Provides ProductEntityStore provideEntityStore(SharedPreferencesProductEntityStore store) {
    return store;
  }

  @Provides
  @Singleton ProductsDataSource provideProductsDataSource(ProductEntityStore productEntityStore, ProductEntityMapper mapper) {
    return new ProductsDataSourceImpl(productEntityStore, mapper);
  }
}
