package pl.charmas.shoppinglist.presentation.base;

public interface LifecycleNotifier extends LifecycleObserver {
  void register(LifecycleObserver observer);

  void unregister(LifecycleObserver observer);
}
