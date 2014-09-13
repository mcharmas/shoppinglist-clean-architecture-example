package pl.charmas.shoppinglist.base;

import android.app.ListActivity;
import android.os.Bundle;

import pl.charmas.shoppinglist.app.ProductListApp;

public class BaseListActivity extends ListActivity {
    private final UILifecycleNotifier notifier = new UILifecycleNotifier();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    private void inject() {
        Object module = createActivityScopedModule();
        if (module != null) {
            ProductListApp.get(this).graph().plus(module).inject(this);
        } else {
            ProductListApp.get(this).inject(this);
        }
    }

    protected Object createActivityScopedModule() {
        return null;
    }

    public void registerUILifecycleObserver(UILifecycleObserver observer) {
        notifier.registerObserver(observer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notifier.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifier.onStop();
    }
}
