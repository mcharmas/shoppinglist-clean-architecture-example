package pl.charmas.shoppinglist.data.store;

import junit.framework.TestCase;

public class InMemoryProductEntityStoreTest extends TestCase {
  public void testShouldContain20DemoProductsAfterCreation() throws Exception {
    InMemoryProductEntityStore store = new InMemoryProductEntityStore();
    int items = store.listAll().size();

    assertEquals(20, items);
  }
}
