package pl.charmas.shoppinglist.products.core.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.products.core.boundaries.ProductBoundary;
import pl.charmas.shoppinglist.products.core.boundaries.ProductListBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class ListProductsUseCase {
    private final ProductsDataSource productsDataSource;

    @Inject
    public ListProductsUseCase(ProductsDataSource productsDataSource) {
        this.productsDataSource = productsDataSource;
    }

    public Observable<ProductListBoundary> execute() {
        return Observable.create(new Observable.OnSubscribe<List<ProductGateway>>() {
            @Override
            public void call(Subscriber<? super List<ProductGateway>> subscriber) {
                subscriber.onNext(productsDataSource.listAll());
                subscriber.onCompleted();
            }
        }).map(new Func1<List<ProductGateway>, ProductListBoundary>() {
            @Override
            public ProductListBoundary call(List<ProductGateway> productGateways) {
                List<ProductBoundary> boundaries = new ArrayList<ProductBoundary>();
                for (ProductGateway productGateway : productGateways) {
                    boundaries.add(new ProductBoundary(productGateway.getId(), productGateway.getName(), productGateway.isBought()));
                }
                return new ProductListBoundary(boundaries);
            }
        });
    }

}
