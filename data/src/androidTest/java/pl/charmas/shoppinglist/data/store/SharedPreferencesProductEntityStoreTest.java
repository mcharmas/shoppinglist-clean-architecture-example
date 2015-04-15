package pl.charmas.shoppinglist.data.store;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import java.util.Arrays;
import java.util.Collections;
import pl.charmas.shoppinglist.data.entity.ProductEntity;

public class SharedPreferencesProductEntityStoreTest extends AndroidTestCase {

  private SharedPreferencesProductEntityStore store;

  @Override public void setUp() throws Exception {
    super.setUp();
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(new IsolatedContext(null, getContext()));
    sp.edit().clear().apply();
    store = new SharedPreferencesProductEntityStore(sp, new SharedPreferencesProductEntityStore.EntityJsonMapper());
    assertEquals(0, store.getAllProduct().size());
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

    assertEquals(2, store.getAllProduct().size());
  }

  public void testShouldIncrementId() throws Exception {
    assertEquals(0, store.getCreateNextId());
    assertEquals(1, store.getCreateNextId());
    assertEquals(2, store.getCreateNextId());
  }
}
