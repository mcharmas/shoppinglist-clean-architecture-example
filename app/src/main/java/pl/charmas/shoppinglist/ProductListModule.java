package pl.charmas.shoppinglist;

import dagger.Module;
import pl.charmas.shoppinglist.products.datasource.ProductsDataSourceModule;
import pl.charmas.shoppinglist.view.ViewModule;

@Module(
        includes = {
                ViewModule.class,
                ProductsDataSourceModule.class
        }
)
public class ProductListModule {
}
