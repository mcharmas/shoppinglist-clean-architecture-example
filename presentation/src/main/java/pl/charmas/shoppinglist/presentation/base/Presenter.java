package pl.charmas.shoppinglist.presentation.base;

public interface Presenter<T extends UI> {
  void attachUI(T ui);

  void detachUI();
}
