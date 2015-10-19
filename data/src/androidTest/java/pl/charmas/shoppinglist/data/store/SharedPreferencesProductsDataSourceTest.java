package pl.charmas.shoppinglist.data.store;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import java.util.Arrays;
import java.util.Collections;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;

public class SharedPreferencesProductsDataSourceTest extends AndroidTestCase {
  private SharedPreferencesProductsDataSource store;

  @Override public void setUp() throws Exception {
    super.setUp();
    store = new SharedPreferencesProductsDataSource(
        getAndClearSharedPreferences(),
        new SharedPreferencesProductsDataSource.ProductListJsonMapper()
    );
  }

  public void testShouldStoreEmptyList() throws Exception {
    store.saveProductList(new ProductList(Collections.<Product>emptyList()));
    assertEquals(store.getProductList().size(), 0);
  }

  public void testShouldStoreListWithMultipleElements() throws Exception {
    store.saveProductList(new ProductList(Arrays.asList(
        new Product(0, "sample", true),
        new Product(1, "sample", false)
    )));

    ProductList products = store.getProductList();
    assertEquals(2, products.size());
  }

  private SharedPreferences getAndClearSharedPreferences() {
    SharedPreferences sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(getContext());
    sharedPreferences.edit().clear().apply();
    return sharedPreferences;
  }
}
