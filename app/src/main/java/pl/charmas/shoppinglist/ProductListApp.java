package pl.charmas.shoppinglist;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public class ProductListApp extends Application {

    private ObjectGraph objectGraph;

    public static ProductListApp get(Context context) {
        return (ProductListApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
    }

    private void initializeDagger() {
        objectGraph = ObjectGraph.create(new ProductListModule());
    }

    public void inject(Object target) {
        objectGraph.inject(target);
    }
}
