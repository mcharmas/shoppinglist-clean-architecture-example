package pl.charmas.shoppinglist.common.rx;

import pl.charmas.shoppinglist.domain.usecase.UseCase;
import pl.charmas.shoppinglist.domain.usecase.UseCaseArgumentless;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    public static <T, A> CallbackHandler<T> callback(final UseCase<T, A> useCase, final A arg) {
        return new CallbackHandler<>(wrap(useCase, arg));
    }

    public static <T> CallbackHandler<T> callback(final UseCaseArgumentless<T> useCase) {
        return new CallbackHandler<>(wrap(useCase));
    }

    public interface AsyncCallback<T> {
        void onResultOk(T result);

        void onError(Throwable throwable);
    }

    public static class CallbackHandler<T> {
        private final Observable<T> observable;

        public CallbackHandler(Observable<T> observable) {
            this.observable = observable;
        }

        public void register(final AsyncCallback<T> resultCallback) {
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<T>() {
                        @Override
                        public void call(T t) {
                            resultCallback.onResultOk(t);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            resultCallback.onError(throwable);
                        }
                    });
        }
    }
}
