package pl.charmas.shoppinglist.view.products.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mcharmas.myapplication.R;

import javax.inject.Inject;

import pl.charmas.shoppinglist.base.BaseActivity;
import pl.charmas.shoppinglist.products.core.boundaries.ProductToAddBoundary;
import pl.charmas.shoppinglist.products.core.usecase.AddProductUseCase;

public class AddProductActivity extends BaseActivity {
    @Inject
    AddProductUseCase addProductUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        final EditText productNameView = (EditText) findViewById(R.id.product_name_view);
        findViewById(R.id.product_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductUseCase.execute(new ProductToAddBoundary(productNameView.getText().toString(), false));
                finish();
            }
        });
    }
}
