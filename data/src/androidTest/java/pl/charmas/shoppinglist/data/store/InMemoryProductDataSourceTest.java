package pl.charmas.shoppinglist.data.store;

import junit.framework.TestCase;

public class InMemoryProductDataSourceTest extends TestCase {
  public void testShouldContain20DemoProductsAfterCreation() throws Exception {
    InMemoryProductDataSource store = new InMemoryProductDataSource();
    int items = store.getProductList().size();

    assertEquals(20, items);
  }
}
