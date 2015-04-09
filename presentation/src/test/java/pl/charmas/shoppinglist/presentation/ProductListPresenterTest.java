package pl.charmas.shoppinglist.presentation;

import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.presentation.model.mappers.ProductToViewModelMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductListPresenterTest {
  @Mock ListProductsUseCase listProductsUseCase;

  @Mock ProductToViewModelMapper mapper;

  @Mock ProductListPresenter.ProductListUI ui;

  ProductListPresenter productListPresenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    productListPresenter = new ProductListPresenter(
        listProductsUseCase, mapper, new TestAsyncUseCase()
    );
  }

  @Test public void testShouldFetchProductListOnStart() throws Exception {
    productListPresenter.attachUI(ui);

    verify(listProductsUseCase, times(1)).execute();
  }

  @Test public void testShouldMapProductsToViewModel() throws Exception {
    List<Product> products = Collections.singletonList(new Product(0, "Sample", false));
    when(listProductsUseCase.execute()).thenReturn(products);

    productListPresenter.attachUI(ui);

    verify(mapper, times(1)).toViewModel(products);
  }

  @Test public void testShouldPresentMappedModelsInUI() throws Exception {
    List<ProductViewModel> vms = Collections.singletonList(new ProductViewModel(0, "name", false));
    when(mapper.toViewModel(Matchers.<List<Product>>any())).thenReturn(vms);

    productListPresenter.attachUI(ui);

    verify(ui, times(1)).showProductList(vms);
  }
}
