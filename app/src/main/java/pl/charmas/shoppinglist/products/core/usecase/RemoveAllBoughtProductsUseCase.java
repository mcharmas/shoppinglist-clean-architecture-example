package pl.charmas.shoppinglist.products.core.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.products.core.boundaries.ProductRemovedBoundary;
import pl.charmas.shoppinglist.products.core.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.products.core.entities.Product;
import pl.charmas.shoppinglist.products.core.gateway.ProductGateway;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class RemoveAllBoughtProductsUseCase {
    private final RemoveProductUseCase removeProductUseCase;
    private final ProductsDataSource productsDataSource;

    @Inject
    public RemoveAllBoughtProductsUseCase(RemoveProductUseCase removeProductUseCase, ProductsDataSource productsDataSource) {
        this.removeProductUseCase = removeProductUseCase;
        this.productsDataSource = productsDataSource;
    }

    public Observable<List<ProductRemovedBoundary>> execute() {
        return Observable
                .create(new FindAllBoughtProducts())
                .flatMap(new MapToSingleProducts())
                .flatMap(new FlatMapToRemoveUseCase())
                .toList();
    }

    private static class MapToSingleProducts implements Func1<List<Product>, Observable<? extends Product>> {
        @Override
        public Observable<? extends Product> call(List<Product> products) {
            return Observable.from(products);
        }
    }

    private class FlatMapToRemoveUseCase implements Func1<Product, Observable<ProductRemovedBoundary>> {
        @Override
        public Observable<ProductRemovedBoundary> call(Product product) {
            return removeProductUseCase.execute(product.getId());
        }
    }

    private class FindAllBoughtProducts implements Observable.OnSubscribe<List<Product>> {
        @Override
        public void call(Subscriber<? super List<Product>> subscriber) {
            List<ProductGateway> allProducts = productsDataSource.listAll();
            List<Product> boughProducts = new ArrayList<>();
            for (ProductGateway productGateway : allProducts) {
                if(productGateway.isBought()) {
                    boughProducts.add(new Product(
                            productGateway.getId(),
                            productGateway.getName(),
                            productGateway.isBought()));
                }
            }

            subscriber.onNext(boughProducts);
            subscriber.onCompleted();
        }
    }
}
