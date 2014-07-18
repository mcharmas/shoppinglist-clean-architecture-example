package pl.charmas.shoppinglist.common.usecase;

public interface UseCase<Result, Argument> {
    Result execute(Argument arg);
}
