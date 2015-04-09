package pl.charmas.shoppinglist.base;

import java.util.HashSet;
import java.util.Set;
import pl.charmas.shoppinglist.presentation.base.LifecycleNotifier;
import pl.charmas.shoppinglist.presentation.base.LifecycleObserver;

class LifecycleNotifierImpl implements LifecycleNotifier {
  private final Set<LifecycleObserver> observers = new HashSet<>();

  @Override public void onStart() {
    for (LifecycleObserver observer : observers) {
      observer.onStart();
    }
  }

  @Override public void onStop() {
    for (LifecycleObserver observer : observers) {
      observer.onStop();
    }
  }

  @Override public void onDestroy() {
    for (LifecycleObserver observer : observers) {
      observer.onDestroy();
    }
  }

  @Override public void register(LifecycleObserver observer) {
    observers.add(observer);
  }

  @Override public void unregister(LifecycleObserver observer) {
    observers.remove(observer);
  }
}
