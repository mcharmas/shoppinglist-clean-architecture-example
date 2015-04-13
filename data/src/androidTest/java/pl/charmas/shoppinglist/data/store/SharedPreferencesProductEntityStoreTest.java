package pl.charmas.shoppinglist.data.store;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

public class SharedPreferencesProductEntityStoreTest extends AndroidTestCase {

  private SharedPreferencesProductEntityStore store;

  @Override public void setUp() throws Exception {
    super.setUp();
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(new IsolatedContext(null, getContext()));
    sp.edit().clear().apply();
    store = new SharedPreferencesProductEntityStore(sp, new SharedPreferencesProductEntityStore.EntityJsonMapper());
    assertEquals(store.listAll().size(), 0);
  }

  public void testShouldCreateProductsWithIncrementedUniqueIds() throws Exception {
    ProductEntity first = store.createProductEntity("first", false);
    ProductEntity second = store.createProductEntity("second", false);
    assertFalse(first == second);
  }

  public void testShouldCreateEntitiesWithProperBoughtStatus() throws Exception {
    ProductEntity first = store.createProductEntity("first", false);
    ProductEntity second = store.createProductEntity("second", true);
    assertFalse(first.isBought());
    assertTrue(second.isBought());
  }
}
