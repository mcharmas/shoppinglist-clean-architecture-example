package pl.charmas.shoppinglist.products.core.usecase;

import javax.inject.Inject;

import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.ProductToAddBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;
import rx.Observable;
import rx.Subscriber;

public class AddProductUseCase {
    private final ProductsDataSource productsDataSource;

    @Inject
    public AddProductUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    public Observable<ProductBoundary> execute(final ProductToAddBoundary productToAddBoundary) {
        return Observable.create(new Observable.OnSubscribe<ProductBoundary>() {
            @Override
            public void call(Subscriber<? super ProductBoundary> subscriber) {
                ProductGateway product = productsDataSource.createProduct(productToAddBoundary.getName(), productToAddBoundary.isBought());
                subscriber.onNext(new ProductBoundary(product.getId(), product.getName(), product.isBought()));
                subscriber.onCompleted();
            }
        });
    }
}
