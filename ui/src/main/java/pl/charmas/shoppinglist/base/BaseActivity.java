package pl.charmas.shoppinglist.base;

import android.app.Activity;
import android.os.Bundle;

import dagger.ObjectGraph;
import pl.charmas.shoppinglist.app.ProductListApp;

public class BaseActivity extends Activity {
    private final UILifecycleNotifier notifier = new UILifecycleNotifier();
    private ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    private void inject() {
        Object module = createActivityScopedModule();
        if (module != null) {
            objectGraph = TestActivityModules.plusTestModules(ProductListApp.get(this).graph().plus(module));
        } else {
            objectGraph = TestActivityModules.plusTestModules(ProductListApp.get(this).graph());
        }
        objectGraph.inject(this);
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
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
