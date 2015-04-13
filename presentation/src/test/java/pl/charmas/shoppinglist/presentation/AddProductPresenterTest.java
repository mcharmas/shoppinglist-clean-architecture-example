package pl.charmas.shoppinglist.presentation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.presentation.AddProductPresenter.AddProductUI;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AddProductPresenterTest {

  @Mock AddProductUI ui;

  private AddProductPresenter presenter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.presenter = new AddProductPresenter();
    this.presenter.attachUI(ui);
  }

  @Test
  public void testShouldValidateEmptyProductName() throws Exception {
    this.presenter.onProductNameComplete("");
    verify(ui, times(1)).showValidationError();
  }

  @Test
  public void testShouldValidateNullProductName() throws Exception {
    this.presenter.onProductNameComplete(null);
    verify(ui, times(1)).showValidationError();
  }

  @Test
  public void testShouldReturnCorrectProductName() throws Exception {
    String productName = "sample product";
    this.presenter.onProductNameComplete(productName);
    verify(ui, times(1)).returnProduct(productName);
  }
}
