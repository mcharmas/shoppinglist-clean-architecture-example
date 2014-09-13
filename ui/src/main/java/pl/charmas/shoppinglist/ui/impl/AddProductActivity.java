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
import pl.charmas.shoppinglist.presenters.AddProductPresenter;
import pl.charmas.shoppinglist.presenters.Presenter;
import pl.charmas.shoppinglist.ui.AddProductUI;

public class AddProductActivity extends BaseActivity implements AddProductUI {

    @Inject
    Presenter<AddProductUI> addProductPresenter;

    private UICallbacks uiCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerUILifecycleObserver(addProductPresenter);
        addProductPresenter.initialize(this);
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
        Presenter<AddProductUI> provideAddProductUI(AddProductPresenter presenter) {
            return presenter;
        }
    }

}
