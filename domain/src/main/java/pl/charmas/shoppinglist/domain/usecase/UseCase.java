package pl.charmas.shoppinglist.domain.usecase;

public interface UseCase<Result, Argument> {
  Result execute(Argument arg) throws Exception;
}
