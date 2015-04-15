package pl.charmas.shoppinglist.domain.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {
  @Test public void testShouldBeBoughtWhenMarkedAsBought() throws Exception {
    Product boughtProduct = new Product(0, "sample name", false).markBought();
    assertEquals(true, boughtProduct.isBought());
  }
}
