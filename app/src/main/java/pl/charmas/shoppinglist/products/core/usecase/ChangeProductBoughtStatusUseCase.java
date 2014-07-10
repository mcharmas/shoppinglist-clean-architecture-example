package pl.charmas.shoppinglist.products.core.usecase;


import javax.inject.Inject;

import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.StatusToChangeBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.entities.Product;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;
import rx.Observable;
import rx.Subscriber;

public class ChangeProductBoughtStatusUseCase {
    private final ProductsDataSource productsDataSource;

    @Inject
    public ChangeProductBoughtStatusUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    public Observable<ProductBoundary> execute(final StatusToChangeBoundary statusToChangeBoundary) {
        return Observable.create(new Observable.OnSubscribe<ProductBoundary>() {
            @Override
            public void call(Subscriber<? super ProductBoundary> subscriber) {
                ProductGateway currentProduct = productsDataSource.getProduct(statusToChangeBoundary.getId());
                Product product = new Product(currentProduct.getId(), currentProduct.getName(), currentProduct.isBought());
                Product updatedProduct;
                if(statusToChangeBoundary.isBought()) {
                    updatedProduct = product.markBought();
                } else {
                    updatedProduct = product.markNotBought();
                }
                productsDataSource.updateProduct(new ProductGateway(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.isBought()));
                subscriber.onNext(new ProductBoundary(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.isBought()));
                subscriber.onCompleted();
            }
        });
    }
}
