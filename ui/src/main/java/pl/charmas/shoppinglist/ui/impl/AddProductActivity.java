package pl.charmas.shoppinglist.ui.impl;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mcharmas.myapplication.R;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pl.charmas.shoppinglist.app.AppModule;
import pl.charmas.shoppinglist.base.BaseActivity;
import pl.charmas.shoppinglist.controller.AddProductController;
import pl.charmas.shoppinglist.controller.Controller;
import pl.charmas.shoppinglist.ui.AddProductUI;

public class AddProductActivity extends BaseActivity implements AddProductUI {

    @Inject
    Controller<AddProductUI> addProductController;

    private UICallbacks uiCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerUILifecycleObserver(addProductController);
        addProductController.initialize(this);
        setContentView(R.layout.activity_add_product);
        final EditText productNameView = (EditText) findViewById(R.id.product_name_view);
        findViewById(R.id.product_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiCallbacks.onAddProductRequest(productNameView.getText().toString());
            }
        });
    }

    @Override
    public void setUICallbacks(UICallbacks callbacks) {
        uiCallbacks = callbacks;
    }

    @Override
    public void navigateBack() {
        finish();
    }

    @Override
    protected Object createActivityScopedModule() {
        return new AddProductModule();
    }

    @Module(injects = {AddProductActivity.class}, addsTo = AppModule.class)
    static class AddProductModule {
        @Provides
        Controller<AddProductUI> provideAddProductUI(AddProductController presenter) {
            return presenter;
        }
    }

}
