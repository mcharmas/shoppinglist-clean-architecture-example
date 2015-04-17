package pl.charmas.shoppinglist.domain.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {
  @Test public void testShouldBeBoughtWhenMarkedAsBought() throws Exception {
    Product product = new Product(0, "sample name", false);
    product.markBought();
    assertEquals(true, product.isBought());
  }
}
