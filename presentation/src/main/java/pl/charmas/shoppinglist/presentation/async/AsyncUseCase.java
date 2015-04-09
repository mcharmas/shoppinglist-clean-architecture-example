package pl.charmas.shoppinglist.presentation.async;

import pl.charmas.shoppinglist.domain.usecase.UseCase;
import pl.charmas.shoppinglist.domain.usecase.UseCaseArgumentless;
import rx.Observable;

public interface AsyncUseCase {
  <T, A> Observable<T> wrap(final UseCase<T, A> useCase, final A arg);

  <T> Observable<T> wrap(final UseCaseArgumentless<T> useCase);
}
