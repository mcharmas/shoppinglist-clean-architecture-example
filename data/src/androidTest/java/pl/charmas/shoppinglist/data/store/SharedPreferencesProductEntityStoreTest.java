package pl.charmas.shoppinglist.data.store;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

public class SharedPreferencesProductEntityStoreTest extends AndroidTestCase {
  private SharedPreferencesProductEntityStore store;

  @Override public void setUp() throws Exception {
    super.setUp();
    store = new SharedPreferencesProductEntityStore(
        getAndClearSharedPreferences(),
        new SharedPreferencesProductEntityStore.EntityJsonMapper()
    );
  }

  public void testShouldStoreEmptyList() throws Exception {
    store.storeAllProducts(Collections.<ProductEntity>emptyList());
    assertEquals(store.getAllProduct().size(), 0);
  }

  public void testShouldStoreListWithMultipleElements() throws Exception {
    store.storeAllProducts(Arrays.asList(
        new ProductEntity(0, "sample", true),
        new ProductEntity(1, "sample", false)
    ));

    List<ProductEntity> products = store.getAllProduct();
    assertEquals(2, products.size());
    assertEquals(0, products.get(0).getId());
    assertEquals(1, products.get(1).getId());
  }

  private SharedPreferences getAndClearSharedPreferences() {
    SharedPreferences sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(getContext());
    sharedPreferences.edit().clear().apply();
    return sharedPreferences;
  }
}
