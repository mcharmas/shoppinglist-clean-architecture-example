package pl.charmas.shoppinglist.presentation;

import javax.inject.Inject;
import pl.charmas.shoppinglist.presentation.base.BasePresenter;
import pl.charmas.shoppinglist.presentation.base.UI;

public class AddProductPresenter extends BasePresenter<AddProductPresenter.AddProductUI> {

  @Inject public AddProductPresenter() {
  }

  public void onProductNameComplete(final String productName) {
    if (productName == null || productName.isEmpty()) {
      execute(new UICommand<AddProductUI>() {
        @Override public void execute(AddProductUI ui) {
          ui.showValidationError();
        }
      }, false);
    } else {
      execute(new UICommand<AddProductUI>() {
        @Override public void execute(AddProductUI ui) {
          ui.returnProduct(productName);
        }
      }, false);
    }
  }

  public interface AddProductUI extends UI {
    void returnProduct(String productName);

    void showValidationError();
  }
}
