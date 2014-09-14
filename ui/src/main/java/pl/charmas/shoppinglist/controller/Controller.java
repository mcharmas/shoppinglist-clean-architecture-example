package pl.charmas.shoppinglist.controller;


import pl.charmas.shoppinglist.base.UILifecycleObserver;

public interface Controller<UI> extends UILifecycleObserver {
    void initialize(UI ui);
}
