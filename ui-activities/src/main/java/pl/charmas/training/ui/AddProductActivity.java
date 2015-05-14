package pl.charmas.training.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import dagger.Component;
import javax.inject.Inject;
import pl.charmas.shoppinglist.presentation.AddProductPresenter;
import pl.charmas.shoppinglist.presentation.AddProductPresenter.AddProductUI;
import pl.charmas.shoppinglist.presentation.scope.PresenterScope;
import pl.charmas.shoppinglist.ui.base.PresenterActivity;

public class AddProductActivity
    extends PresenterActivity<AddProductUI, AddProductActivity.AddProductComponent>
    implements AddProductUI {

  @Inject AddProductPresenter presenter;

  private EditText productNameView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
    setupPresenter(presenter, this);
    setContentView(R.layout.activity_add_product);
    productNameView = (EditText) findViewById(R.id.product_name_view);
    findViewById(R.id.product_add_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        presenter.onProductNameComplete(productNameView.getText().toString());
      }
    });
  }

  @Override protected AddProductComponent onCreateComponent() {
    ProductListApp.AppComponent appComponent = ((ProductListApp) getApplication()).getComponent();
    return DaggerAddProductActivity_AddProductComponent.builder()
        .appComponent(appComponent)
        .build();
  }

  @Override public void navigateBack() {
    setResult(Activity.RESULT_OK);
    finish();
  }

  @Override public void showValidationError() {
    Toast.makeText(this, "Product name cannot be empty", Toast.LENGTH_SHORT).show();
  }

  @PresenterScope
  @Component(dependencies = ProductListApp.AppComponent.class)
  public interface AddProductComponent {
    void inject(AddProductActivity target);

    AddProductPresenter getPresenter();
  }
}
