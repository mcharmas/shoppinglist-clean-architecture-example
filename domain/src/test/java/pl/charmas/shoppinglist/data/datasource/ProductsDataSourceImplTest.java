package pl.charmas.shoppinglist.data.datasource;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.data.entity.mappers.ProductEntityMapper;
import pl.charmas.shoppinglist.data.store.ProductEntityStore;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked") public class ProductsDataSourceImplTest {
  @Mock ProductEntityStore store;
  ProductsDataSourceImpl dataSource;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    dataSource = new ProductsDataSourceImpl(store, new ProductEntityMapper());
  }

  @Test
  public void testShouldMapProductListAndStoreIt() throws Exception {
    dataSource.saveProductList(new ProductList(Arrays.asList(
        new Product(0, "test", true),
        new Product(1, "test", true)
    )));

    ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
    verify(store, times(1)).storeAllProducts(captor.capture());

    assertEquals(captor.getValue().size(), 2);
  }
}
