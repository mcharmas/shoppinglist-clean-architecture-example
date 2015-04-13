package pl.charmas.shoppinglist.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import java.util.List;
import pl.charmas.shoppinglist.presentation.base.Presenter;
import pl.charmas.shoppinglist.presentation.base.UI;
import pl.charmas.shoppinglist.ui.base.injectors.FragmentInjector;
import pl.charmas.shoppinglist.ui.base.injectors.ModuleFactory;

public class PresenterFragment<T extends UI> extends Fragment implements ModuleFactory {
  private Presenter<T> presenter;
  private T ui;
  private FragmentInjector fragmentInjector;

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      PresenterFragmentActivity presenterActivity = (PresenterFragmentActivity) activity;
      fragmentInjector = new FragmentInjector(getId(), presenterActivity, this);
      fragmentInjector.inject(this);
    } catch (ClassCastException ex) {
      throw new IllegalStateException("This fragment must be contained by BasePresenterFragmentActivity");
    }
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
      fragmentInjector.destroyGraph();
    }
  }

  @Override public void prepareInstanceModules(List<Object> modules) {

  }

  @Override public void preparePresenterModules(List<Object> modules) {

  }
}
