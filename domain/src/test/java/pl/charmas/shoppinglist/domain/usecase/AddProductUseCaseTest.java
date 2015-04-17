package pl.charmas.shoppinglist.domain.usecase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.ProductList;
import pl.charmas.shoppinglist.domain.usecase.exceptions.ValidationException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AddProductUseCaseTest {
  private static final String PRODUCT_NAME = "Sample product";
  @Mock ProductsDataSource productsDataSourceMock;
  @Mock ProductList mockProducts;

  private AddProductUseCase useCase;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    useCase = new AddProductUseCase(productsDataSourceMock);
  }

  @Test public void testShouldAddProduct() throws Exception {
    when(productsDataSourceMock.getProductList()).thenReturn(mockProducts);

    useCase.execute(PRODUCT_NAME);

    verify(mockProducts, times(1)).addProduct(PRODUCT_NAME);
    verify(productsDataSourceMock).saveProductList(mockProducts);
  }

  @Test
  public void testShouldThrowWhenProductNameNullEmptyOrSpaces() throws Exception {
    assertFailsWithIllegalArgumentException(null);
    assertFailsWithIllegalArgumentException("");
    assertFailsWithIllegalArgumentException("   ");
  }

  private void assertFailsWithIllegalArgumentException(String productName) throws Exception {
    try {
      useCase.execute(productName);
      Assert.fail();
    } catch (ValidationException ex) {
      verifyZeroInteractions(productsDataSourceMock);
    }
  }
}
