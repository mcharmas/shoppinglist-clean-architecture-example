package pl.charmas.shoppinglist.presentation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.usecase.AddProductUseCase;
import pl.charmas.shoppinglist.domain.usecase.exceptions.ValidationException;
import pl.charmas.shoppinglist.presentation.AddProductPresenter.AddProductUI;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddProductPresenterTest {

  @Mock AddProductUI ui;

  @Mock AddProductUseCase addProductUseCase;

  private AddProductPresenter presenter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.presenter = new AddProductPresenter(addProductUseCase, new TestAsyncUseCase());
    this.presenter.attachUI(ui);
  }

  @Test
  public void testShouldValidateNotifyUIOnValidationError() throws Exception {
    when(addProductUseCase.execute(anyString())).thenThrow(new ValidationException(""));
    this.presenter.onProductNameComplete("");
    verify(ui, times(1)).showValidationError();
  }

  @Test
  public void testShouldReturnCorrectProductName() throws Exception {
    String productName = "sample product";
    this.presenter.onProductNameComplete(productName);
    verify(addProductUseCase, times(1)).execute(productName);
  }

  @Test
  public void testShouldNavigateBackOnProductAdded() throws Exception {
    when(addProductUseCase.execute(anyString())).thenReturn(new Product(0, "", false));
    this.presenter.onProductNameComplete("sample product");
    verify(ui, times(1)).navigateBack();
  }
}
