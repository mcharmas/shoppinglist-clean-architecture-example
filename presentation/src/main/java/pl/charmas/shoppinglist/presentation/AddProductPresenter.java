package pl.charmas.shoppinglist.presentation;

import javax.inject.Inject;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.usecase.AddProductUseCase;
import pl.charmas.shoppinglist.domain.usecase.exceptions.ValidationException;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;
import pl.charmas.shoppinglist.presentation.base.BasePresenter;
import pl.charmas.shoppinglist.presentation.base.UI;
import rx.functions.Action1;

@Singleton
public class AddProductPresenter extends BasePresenter<AddProductPresenter.AddProductUI> {
  private final AddProductUseCase addProductUseCase;
  private final AsyncUseCase asyncUseCase;

  @Inject public AddProductPresenter(AddProductUseCase addProductUseCase, AsyncUseCase asyncUseCase) {
    this.addProductUseCase = addProductUseCase;
    this.asyncUseCase = asyncUseCase;
  }

  public void onProductNameComplete(final String productName) {
    asyncUseCase.wrap(addProductUseCase, productName)
        .subscribe(
            new Action1<Product>() {
              @Override public void call(Product product) {
                executeRepeat(new NavigateBackCommand());
              }
            },
            new Action1<Throwable>() {
              @Override public void call(Throwable throwable) {
                if (throwable instanceof ValidationException) {
                  executeOnce(new ShowValidationErrorCommand());
                }
                // TODO: handle unknown error
              }
            });
  }

  public interface AddProductUI extends UI {
    void navigateBack();

    void showValidationError();
  }

  private static class ShowValidationErrorCommand implements UICommand<AddProductUI> {
    @Override public void execute(AddProductUI ui) {
      ui.showValidationError();
    }
  }

  private static class NavigateBackCommand implements UICommand<AddProductUI> {
    @Override public void execute(AddProductUI ui) {
      ui.navigateBack();
    }
  }
}
