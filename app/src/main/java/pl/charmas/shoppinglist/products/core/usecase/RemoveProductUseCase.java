package pl.charmas.shoppinglist.products.core.usecase;

import javax.inject.Inject;

import pl.charmas.shoppinglist.products.core.boundaries.ProductRemovedBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import rx.Observable;
import rx.Subscriber;

public class RemoveProductUseCase {
    private final ProductsDataSource productsDataSource;

    @Inject
    public RemoveProductUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    public Observable<ProductRemovedBoundary> execute(final long productId) {
        return Observable.create(new Observable.OnSubscribe<ProductRemovedBoundary>() {
            @Override
            public void call(Subscriber<? super ProductRemovedBoundary> subscriber) {
                productsDataSource.removeProduct(productId);
                subscriber.onNext(new ProductRemovedBoundary(productId));
                subscriber.onCompleted();
            }
        });
    }

}
