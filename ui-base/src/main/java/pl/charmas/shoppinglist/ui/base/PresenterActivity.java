package pl.charmas.shoppinglist.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import pl.charmas.shoppinglist.presentation.base.Presenter;
import pl.charmas.shoppinglist.presentation.base.UI;

public abstract class PresenterActivity<T extends UI, Component> extends ActionBarActivity {
  private Presenter<T> presenter;
  private T ui;
  private Component component;

  @SuppressWarnings("unchecked")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component = (Component) getLastCustomNonConfigurationInstance();
  }

  @Override public Object onRetainCustomNonConfigurationInstance() {
    return component;
  }

  protected abstract Component onCreateComponent();

  protected Component getComponent() {
    if (component == null) component = onCreateComponent();
    return component;
  }

  protected void setupPresenter(Presenter<T> presenter, T ui) {
    this.presenter = presenter;
    this.ui = ui;
  }

  @Override protected void onStart() {
    super.onStart();
    presenter.attachUI(ui);
  }

  @Override protected void onStop() {
    super.onStop();
    presenter.detachUI();
  }
}
