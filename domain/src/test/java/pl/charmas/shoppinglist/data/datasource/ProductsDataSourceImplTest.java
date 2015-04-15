package pl.charmas.shoppinglist.data.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.data.entity.mappers.ProductEntityMapper;
import pl.charmas.shoppinglist.data.store.ProductEntityStore;
import pl.charmas.shoppinglist.domain.entities.Product;

import static org.junit.Assert.assertEquals;

public class ProductsDataSourceImplTest {

  @Mock ProductEntityStore store;

  ProductsDataSourceImpl dataSource;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    dataSource = new ProductsDataSourceImpl(store, new ProductEntityMapper());
  }

  @Test
  public void testShouldCreateProductWithProperId() throws Exception {
    long availableId = 5l;
    Mockito.when(store.getCreateNextId()).thenReturn(availableId);

    Product product = dataSource.createProduct("Sample", true);

    assertEquals(product.getId(), 5);
  }
}
