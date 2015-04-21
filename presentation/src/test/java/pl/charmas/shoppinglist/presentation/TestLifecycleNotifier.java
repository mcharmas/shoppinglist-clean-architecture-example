package pl.charmas.shoppinglist.presentation;

import pl.charmas.shoppinglist.presentation.base.LifecycleNotifier;
import pl.charmas.shoppinglist.presentation.base.LifecycleObserver;

public class TestLifecycleNotifier implements LifecycleNotifier {
  private LifecycleObserver observer;

  @Override public void register(LifecycleObserver observer) {
    this.observer = observer;
  }

  @Override public void unregister(LifecycleObserver observer) {
    this.observer = null;
  }

  @Override public void onStart() {
    if (hasObserver()) this.observer.onStart();
  }

  @Override public void onStop() {
    if (hasObserver()) this.observer.onStop();
  }

  @Override public void onDestroy() {
    if (hasObserver()) this.observer.onDestroy();
  }

  private boolean hasObserver() {
    return this.observer != null;
  }
}
