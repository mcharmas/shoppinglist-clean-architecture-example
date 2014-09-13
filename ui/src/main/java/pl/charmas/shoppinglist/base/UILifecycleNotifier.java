package pl.charmas.shoppinglist.base;


import java.util.LinkedList;
import java.util.List;

public class UILifecycleNotifier implements UILifecycleObserver {
    private final List<UILifecycleObserver> observers = new LinkedList<>();

    public void registerObserver(UILifecycleObserver uiLifecycleObserver) {
        observers.add(uiLifecycleObserver);
    }

    @Override
    public void onStart() {
        for (UILifecycleObserver observer : observers) {
            observer.onStart();
        }
    }

    @Override
    public void onStop() {
        for (UILifecycleObserver observer : observers) {
            observer.onStop();
        }
    }
}
