package pl.charmas.shoppinglist.domain.usecase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.ProductList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AddProductUseCaseTest {
  @Mock ProductsDataSource productsDataSourceMock;

  private AddProductUseCase useCase;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    useCase = new AddProductUseCase(productsDataSourceMock);
  }

  @Test public void testShouldAddProduct() throws Exception {
    ProductList productList = new ProductList(null);
    when(productsDataSourceMock.getProductList()).thenReturn(productList);

    useCase.execute("Sample product");

    verify(productsDataSourceMock).saveProductList(productList);
  }

  @Test
  public void testShouldThrowWhenProductNameNullEmptyOrSpaces() throws Exception {
    assertFailsWithIllegalArgumentException(null);
    assertFailsWithIllegalArgumentException("");
    assertFailsWithIllegalArgumentException("   ");
  }

  private void assertFailsWithIllegalArgumentException(String productName) {
    try {
      useCase.execute(productName);
      Assert.fail();
    } catch (IllegalArgumentException ex) {
      verifyZeroInteractions(productsDataSourceMock);
    }
  }
}
