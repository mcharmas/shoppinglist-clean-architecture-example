package pl.charmas.shoppinglist.domain.entities;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ProductListTest {

  private static final int PRODUCT_ID_NOT_BOUGHT = 0;
  private static final int PRODUCT_ID_BOUGHT = 1;
  private static final String SAMPLE_PRODUCT_NAME = "sample product";

  @Test public void testShouldCreateEmptyProductList() throws Exception {
    ProductList productList = new ProductList(null);
    assertEquals(0, productList.size());
  }

  @Test public void testShouldHaveBiggerSizeAfterCreateProduct() throws Exception {
    ProductList products = new ProductList(null);
    Product createdProduct = products.addProduct(SAMPLE_PRODUCT_NAME);
    assertNotNull(createdProduct);
    assertEquals(SAMPLE_PRODUCT_NAME, createdProduct.getName());
    assertEquals(false, createdProduct.isBought());
    assertEquals(1, products.size());
  }

  @Test public void testShouldCreateProduct() throws Exception {
    ProductList products = new ProductList(null);
    Product product = products.addProduct(SAMPLE_PRODUCT_NAME);
    assertNotNull(product);
  }

  @Test public void testShouldRemoveOnlyBoughtProducts() throws Exception {
    ProductList products = new ProductList(Arrays.asList(
        new Product(PRODUCT_ID_NOT_BOUGHT, SAMPLE_PRODUCT_NAME, false),
        new Product(PRODUCT_ID_BOUGHT, SAMPLE_PRODUCT_NAME, true)
    ));

    int removedCount = products.removeBoughtProducts();

    assertEquals(1, removedCount);
    assertEquals(1, products.size());
    assertNull(products.getProduct(PRODUCT_ID_BOUGHT));
    assertNotNull(products.getProduct(PRODUCT_ID_NOT_BOUGHT));
  }

  @Test public void testShouldSetLowestAvailableId() throws Exception {
    ProductList products = new ProductList(Arrays.asList(
        new Product(10, SAMPLE_PRODUCT_NAME, false),
        new Product(20, SAMPLE_PRODUCT_NAME, true)
    ));

    Product product = products.addProduct(SAMPLE_PRODUCT_NAME);
    assertEquals(21, product.getId());
  }
}
