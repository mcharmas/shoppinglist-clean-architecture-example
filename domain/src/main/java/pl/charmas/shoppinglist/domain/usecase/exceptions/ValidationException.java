package pl.charmas.shoppinglist.domain.usecase.exceptions;

public class ValidationException extends Exception {
  public ValidationException(String message) {
    super(message);
  }
}
