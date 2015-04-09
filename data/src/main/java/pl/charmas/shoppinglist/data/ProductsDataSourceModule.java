package pl.charmas.shoppinglist.data;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.datasource.ProductsDataSourceImpl;
import pl.charmas.shoppinglist.data.entity.mappers.ProductEntityMapper;
import pl.charmas.shoppinglist.data.store.InMemoryProductEntityStore;
import pl.charmas.shoppinglist.data.store.ProductEntityStore;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;

@Module(complete = true, library = true)
public class ProductsDataSourceModule {

  @Provides ProductEntityStore provideEntityStore(InMemoryProductEntityStore inMemoryProductEntityStore) {
    return inMemoryProductEntityStore;
  }

  @Provides
  @Singleton ProductsDataSource provideProductsDataSource(ProductEntityStore productEntityStore, ProductEntityMapper mapper) {
    return new ProductsDataSourceImpl(productEntityStore, mapper);
  }
}
