package pl.charmas.shoppinglist.common.rx;

import pl.charmas.shoppinglist.common.usecase.UseCase;
import pl.charmas.shoppinglist.common.usecase.UseCaseArgumentless;
import rx.Observable;
import rx.Subscriber;

public class AsyncUseCase {
    public static <T, A> Observable<T> wrap(final UseCase<T, A> useCase, final A arg) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(useCase.execute(arg));
                subscriber.onCompleted();
            }
        });
    }

    public static <T> Observable<T> wrap(final UseCaseArgumentless<T> useCase) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(useCase.execute());
                subscriber.onCompleted();
            }
        });
    }
}
