package pl.charmas.shoppinglist.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import pl.charmas.shoppinglist.presentation.base.Presenter;
import pl.charmas.shoppinglist.presentation.base.UI;

public class PresenterFragment<T extends UI> extends Fragment {
  private Presenter<T> presenter;
  private T ui;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
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
}
