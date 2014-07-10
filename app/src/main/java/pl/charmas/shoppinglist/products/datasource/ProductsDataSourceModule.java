package pl.charmas.shoppinglist.products.datasource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;

@Module(complete = true, library = true)
public class ProductsDataSourceModule {
    @Provides
    @Singleton
    ProductsDataSource provideProductsDataSource() {
        return new ProductsDataSourceImpl();
    }
}
