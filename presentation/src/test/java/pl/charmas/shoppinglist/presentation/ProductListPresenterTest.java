package pl.charmas.shoppinglist.presentation;

import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.entities.ProductList;
import pl.charmas.shoppinglist.domain.usecase.ChangeProductBoughtStatusUseCase;
import pl.charmas.shoppinglist.domain.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.domain.usecase.RemoveAllBoughtProductsUseCase;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.presentation.model.mappers.ProductToViewModelMapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductListPresenterTest {
  @Mock ListProductsUseCase listProductsUseCase;

  @Mock ChangeProductBoughtStatusUseCase changeProductStatusUseCase;

  @Mock RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase;

  @Mock ProductToViewModelMapper mapper;

  @Mock ProductListPresenter.ProductListUI ui;

  ProductListPresenter presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new ProductListPresenter(
        listProductsUseCase, changeProductStatusUseCase, removeAllBoughtProductsUseCase, mapper, new TestAsyncUseCase()
    );
  }

  @Test public void testShouldFetchProductListOnStart() throws Exception {
    presenter.attachUI(ui);

    verify(listProductsUseCase, times(1)).execute();
  }

  @Test public void testShouldMapProductsToViewModel() throws Exception {
    ProductList products = new ProductList(Collections.singletonList(new Product(0, "Sample", false)));
    when(listProductsUseCase.execute()).thenReturn(products);

    presenter.attachUI(ui);

    verify(mapper, times(1)).toViewModel(products);
  }

  @Test public void testShouldPresentMappedModelsInUI() throws Exception {
    List<ProductViewModel> vms = Collections.singletonList(new ProductViewModel(0, "name", false));
    when(mapper.toViewModel(Matchers.<ProductList>any())).thenReturn(vms);

    presenter.attachUI(ui);

    verify(ui, times(1)).showProductList(vms);
  }

  @Test public void testShouldNavigateToAddProductScreen() throws Exception {
    presenter.attachUI(ui);
    presenter.onAddNewProduct();
    verify(ui, times(1)).navigateToAddProduct();
  }

  @Test public void testShouldChangeProductStatusAndRefreshListWithNoProgressShown() throws Exception {
    presenter.productStatusChanged(0l, true);
    presenter.attachUI(ui);

    ArgumentCaptor<ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest> argument =
        ArgumentCaptor.forClass(ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest.class);
    verify(changeProductStatusUseCase, times(1)).execute(argument.capture());
    assertEquals(argument.getValue().boughtStatus, true);
    assertEquals(argument.getValue().productId, 0l);

    verify(listProductsUseCase, times(2)).execute();
    verify(ui, times(1)).showProgress();
  }

  @Test
  public void testShouldRemoveAllBoughtProductsAndRefreshTheListWithProgress() throws Exception {
    presenter.onRemoveBoughtProducts();
    presenter.attachUI(ui);

    verify(removeAllBoughtProductsUseCase, times(1)).execute();
    verify(listProductsUseCase, times(2)).execute();
    verify(ui, times(2)).showProgress();
  }
}
