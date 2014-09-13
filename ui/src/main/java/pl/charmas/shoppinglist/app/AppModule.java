package pl.charmas.shoppinglist.app;

import dagger.Module;
import pl.charmas.shoppinglist.data.ProductsDataSourceModule;

@Module(includes = {ProductsDataSourceModule.class})
public class AppModule {
}
