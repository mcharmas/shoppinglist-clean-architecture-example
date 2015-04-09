package pl.charmas.shoppinglist.presentation.base;

public interface LifecycleObserver {
  void onStart();

  void onStop();

  void onDestroy();

  class LifecycleObserverAdapter implements LifecycleObserver {
    @Override public void onStart() {
    }

    @Override public void onStop() {
    }

    @Override public void onDestroy() {
    }
  }
}
