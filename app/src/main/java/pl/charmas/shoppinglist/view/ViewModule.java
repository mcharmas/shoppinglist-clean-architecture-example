package pl.charmas.shoppinglist.view;

import dagger.Module;
import pl.charmas.shoppinglist.view.products.ui.AddProductActivity;
import pl.charmas.shoppinglist.view.products.ui.ProductListActivity;

@Module(injects = {
        ProductListActivity.class,
        AddProductActivity.class
}, complete = false)
public class ViewModule {
}
