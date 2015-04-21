package pl.charmas.shoppinglist.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.ProductList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RemoveAllBoughtProductsUseCaseTest {
  @Mock ProductsDataSource productsDataSource;
  @Mock ProductList mockProducts;

  private RemoveAllBoughtProductsUseCase useCase;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    useCase = new RemoveAllBoughtProductsUseCase(productsDataSource);
  }

  @Test
  public void testShouldRemoveBoughtProductsAndSaveTheList() throws Exception {
    when(productsDataSource.getProductList()).thenReturn(mockProducts);

    useCase.execute();

    verify(mockProducts, times(1)).removeBoughtProducts();
    verify(productsDataSource, times(1)).saveProductList(mockProducts);
  }
}
