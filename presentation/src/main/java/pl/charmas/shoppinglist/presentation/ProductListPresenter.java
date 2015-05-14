package pl.charmas.shoppinglist.presentation;

import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.domain.entities.ProductList;
import pl.charmas.shoppinglist.domain.usecase.ChangeProductBoughtStatusUseCase;
import pl.charmas.shoppinglist.domain.usecase.ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest;
import pl.charmas.shoppinglist.domain.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.domain.usecase.RemoveAllBoughtProductsUseCase;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;
import pl.charmas.shoppinglist.presentation.base.BasePresenter;
import pl.charmas.shoppinglist.presentation.base.UI;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.presentation.model.mappers.ProductToViewModelMapper;
import pl.charmas.shoppinglist.presentation.scope.PresenterScope;
import rx.functions.Action1;

@PresenterScope
public class ProductListPresenter extends BasePresenter<ProductListPresenter.ProductListUI> {
  private final ListProductsUseCase listProductsUseCase;
  private final ChangeProductBoughtStatusUseCase changeProductStatusUseCase;
  private final RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase;
  private final ProductToViewModelMapper mapper;
  private final AsyncUseCase asyncUseCase;

  @Inject
  public ProductListPresenter(final ListProductsUseCase listProductsUseCase, ChangeProductBoughtStatusUseCase changeProductStatusUseCase,
      RemoveAllBoughtProductsUseCase removeAllBoughtProductsUseCase, ProductToViewModelMapper mapper, AsyncUseCase asyncUseCase) {
    this.listProductsUseCase = listProductsUseCase;
    this.changeProductStatusUseCase = changeProductStatusUseCase;
    this.removeAllBoughtProductsUseCase = removeAllBoughtProductsUseCase;
    this.mapper = mapper;
    this.asyncUseCase = asyncUseCase;
  }

  @Override protected void onFirstUIAttachment() {
    updateProductList(true);
  }

  public void productStatusChanged(long productId, boolean isBought) {
    asyncUseCase.wrap(changeProductStatusUseCase,
        new ChangeProductStatusRequest(productId, isBought))
        .subscribe(new Action1<Void>() {
          @Override public void call(Void v) {
            updateProductList(false);
          }
        });
  }

  public void onAddNewProduct() {
    executeOnce(new UICommand<ProductListUI>() {
      @Override public void execute(ProductListUI ui) {
        ui.navigateToAddProduct();
      }
    });
  }

  public void onRemoveBoughtProducts() {
    asyncUseCase.wrap(removeAllBoughtProductsUseCase).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        updateProductList(true);
      }
    });
  }

  public void onProductAdded() {
    updateProductList(true);
  }

  private void updateProductList(boolean withProgress) {
    if (withProgress) {
      execute(new ShowProgressCommand(), true);
    }
    asyncUseCase.wrap(listProductsUseCase).subscribe(
        new Action1<ProductList>() {
          @Override public void call(ProductList products) {
            List<ProductViewModel> viewModels = mapper.toViewModel(products);
            executeRepeat(new PresentContentCommand(viewModels));
          }
        }
    );
  }

  public interface ProductListUI extends UI {
    void showProductList(List<ProductViewModel> productViewModels);

    void showProgress();

    void navigateToAddProduct();
  }

  private static class ShowProgressCommand implements UICommand<ProductListUI> {
    @Override public void execute(ProductListUI ui) {
      ui.showProgress();
    }
  }

  private static class PresentContentCommand implements UICommand<ProductListUI> {
    private final List<ProductViewModel> products;

    public PresentContentCommand(List<ProductViewModel> products) {
      this.products = products;
    }

    @Override public void execute(ProductListUI ui) {
      ui.showProductList(this.products);
    }
  }
}
