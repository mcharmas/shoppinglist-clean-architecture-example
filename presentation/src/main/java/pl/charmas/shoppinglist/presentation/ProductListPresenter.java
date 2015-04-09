package pl.charmas.shoppinglist.presentation;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.usecase.ChangeProductBoughtStatusUseCase;
import pl.charmas.shoppinglist.domain.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.domain.usecase.RemoveAllBoughtProductsUseCase;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;
import pl.charmas.shoppinglist.presentation.base.BasePresenter;
import pl.charmas.shoppinglist.presentation.base.UI;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.presentation.model.mappers.ProductToViewModelMapper;
import rx.functions.Action1;

@Singleton
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

  private void updateProductList(boolean withProgress) {
    if (withProgress) {
      execute(new ShowProgressCommand(), true);
    }
    asyncUseCase.wrap(listProductsUseCase).subscribe(
        new Action1<List<Product>>() {
          @Override public void call(List<Product> products) {
            execute(new PresentContentCommand(products), true);
          }
        }
    );
  }

  public void productStatusChanged(long productId, boolean isBought) {
    asyncUseCase.wrap(changeProductStatusUseCase, new ChangeProductBoughtStatusUseCase.ChangeProductStatusRequest(productId, isBought))
        .subscribe(new Action1<Product>() {
          @Override public void call(Product product) {
            updateProductList(false);
          }
        });
  }

  public void onAddNewProduct() {
    execute(new UICommand<ProductListUI>() {
      @Override public void execute(ProductListUI ui) {
        ui.navigateToAddProduct();
      }
    }, false);
  }

  public void onRemoveBoughtProducts() {
    asyncUseCase.wrap(removeAllBoughtProductsUseCase).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        updateProductList(true);
      }
    });
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

  private class PresentContentCommand implements UICommand<ProductListUI> {
    private final List<Product> products;

    public PresentContentCommand(List<Product> products) {
      this.products = products;
    }

    @Override public void execute(ProductListUI ui) {
      ui.showProductList(mapper.toViewModel(products));
    }
  }
}
