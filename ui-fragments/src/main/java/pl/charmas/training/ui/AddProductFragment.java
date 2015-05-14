package pl.charmas.training.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import dagger.Component;
import javax.inject.Inject;
import pl.charmas.shoppinglist.presentation.AddProductPresenter;
import pl.charmas.shoppinglist.presentation.AddProductPresenter.AddProductUI;
import pl.charmas.shoppinglist.presentation.scope.PresenterScope;
import pl.charmas.shoppinglist.ui.base.PresenterFragment;

public class AddProductFragment
    extends PresenterFragment<AddProductUI, AddProductFragment.AddProductComponent>
    implements AddProductUI {

  @Inject AddProductPresenter presenter;

  private EditText productNameView;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
    setupPresenter(presenter, this);
  }

  @Override protected AddProductComponent onCreateComponent() {
    return DaggerAddProductFragment_AddProductComponent.builder()
        .appComponent(((ProductListApp) getActivity().getApplication()).getComponent())
        .build();
  }

  @Override public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_add_product, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    productNameView = (EditText) view.findViewById(R.id.product_name_view);
    view.findViewById(R.id.product_add_button)
        .setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            presenter.onProductNameComplete(productNameView.getText().toString());
          }
        });
  }

  @Override public void navigateBack() {
    ((ProductListFragmentActivity) getActivity()).onProductAdded();
  }

  @Override public void showValidationError() {
    Toast.makeText(getActivity(), "Product name cannot be empty.", Toast.LENGTH_SHORT).show();
  }

  @PresenterScope
  @Component(dependencies = ProductListApp.AppComponent.class)
  public interface AddProductComponent {
    void inject(AddProductFragment target);
  }
}
