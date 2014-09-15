package pl.charmas.shoppinglist.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RemoveAllBoughtProductsUseCaseTest {

    @Mock
    ProductsDataSource productsDataSource;

    @Mock
    RemoveProductUseCase removeProductUseCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShouldRemoveOnlyBoughtProducts() throws Exception {
        Product boughtProduct = new Product(0, "Bought Product", true);
        Product notBoughtProduct = new Product(1, "Not Bough Product", false);
        List<Product> testProducts = Arrays.asList(boughtProduct, notBoughtProduct);
        when(productsDataSource.listAll()).thenReturn(testProducts);

        RemoveAllBoughtProductsUseCase useCase = createUseCaseWithMocks();
        useCase.execute();

        verify(removeProductUseCase, times(1)).execute(eq(boughtProduct.getId()));
        verify(removeProductUseCase, never()).execute(eq(notBoughtProduct.getId()));
    }

    private RemoveAllBoughtProductsUseCase createUseCaseWithMocks() {
        return new RemoveAllBoughtProductsUseCase(removeProductUseCase, productsDataSource);
    }
}