package pl.charmas.shoppinglist.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import pl.charmas.shoppinglist.presentation.base.Presenter;
import pl.charmas.shoppinglist.presentation.base.UI;

public abstract class PresenterFragment<T extends UI, Component> extends Fragment {
  private static final String STATE_UUID = "STATE_UUID";

  private Presenter<T> presenter;
  private T ui;
  private Component component;
  private String fragmentUUID;

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof PresenterFragmentActivity)) {
      throw new ClassCastException("This fragment must be contained by PresenterFragmentActivity");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      fragmentUUID = java.util.UUID.randomUUID().toString();
    } else {
      fragmentUUID = savedInstanceState.getString(STATE_UUID);
    }
    component = (Component) getPresenterActivity().getComponentForFragment(fragmentUUID);
  }

  private PresenterFragmentActivity getPresenterActivity() {
    return (PresenterFragmentActivity) getActivity();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(STATE_UUID, fragmentUUID);
  }

  protected abstract Component onCreateComponent();

  protected Component getComponent() {
    if (component == null) {
      component = onCreateComponent();
      getPresenterActivity().setComponentForFragment(fragmentUUID, component);
    }
    return component;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    presenter.attachUI(ui);
  }

  public void setupPresenter(Presenter<T> presenter, T ui) {
    this.presenter = presenter;
    this.ui = ui;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.detachUI();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (!getActivity().isChangingConfigurations()) {
      getPresenterActivity().clearComponentForFragment(fragmentUUID);
    }
  }
}
