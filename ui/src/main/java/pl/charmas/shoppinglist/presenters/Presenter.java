package pl.charmas.shoppinglist.presenters;


import pl.charmas.shoppinglist.base.UILifecycleObserver;

public interface Presenter<UI> extends UILifecycleObserver {
    void initialize(UI ui);
}
