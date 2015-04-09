package pl.charmas.shoppinglist.presentation;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.domain.usecase.ListProductsUseCase;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;
import pl.charmas.shoppinglist.presentation.base.BasePresenter;
import pl.charmas.shoppinglist.presentation.base.UI;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.presentation.model.mappers.ProductToViewModelMapper;
import rx.functions.Action1;

@Singleton
public class ProductListPresenter extends BasePresenter<ProductListPresenter.ProductListUI> {
  private final ListProductsUseCase listProductsUseCase;
  private final ProductToViewModelMapper mapper;
  private final AsyncUseCase asyncUseCase;

  @Inject
  public ProductListPresenter(final ListProductsUseCase listProductsUseCase, ProductToViewModelMapper mapper, AsyncUseCase asyncUseCase) {
    this.listProductsUseCase = listProductsUseCase;
    this.mapper = mapper;
    this.asyncUseCase = asyncUseCase;
  }

  @Override protected void onFirstUIAttachment() {
    fetchProductList();
  }

  private void fetchProductList() {
    execute(new ShowProgressCommand(), true);
    asyncUseCase.wrap(listProductsUseCase).subscribe(
        new Action1<List<Product>>() {
          @Override public void call(List<Product> products) {
            execute(new PresentContentCommand(products), true);
          }
        }
    );
  }

  public void productStatusChanged(long productId, boolean isBought) {
    //TODO implement
  }

  public void onAddNewProduct() {
    //TODO implement
  }

  public void onRemoveBoughtProducts() {
    //TODO implement
  }

  public interface ProductListUI extends UI {
    void showProductList(List<ProductViewModel> productViewModels);

    void showProgress();
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
