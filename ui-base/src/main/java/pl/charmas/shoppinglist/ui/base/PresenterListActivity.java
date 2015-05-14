package pl.charmas.shoppinglist.ui.base;

import android.app.ListActivity;
import android.os.Bundle;
import java.util.List;
import pl.charmas.shoppinglist.presentation.base.Presenter;
import pl.charmas.shoppinglist.presentation.base.UI;
import pl.charmas.shoppinglist.ui.base.injectors.ActivityInjector;
import pl.charmas.shoppinglist.ui.base.injectors.Injector;
import pl.charmas.shoppinglist.ui.base.injectors.ModuleFactory;

public class PresenterListActivity<T extends UI> extends ListActivity implements ModuleFactory {
  private Presenter<T> presenter;
  private T ui;
  private ActivityInjector activityInjector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //noinspection deprecation
    activityInjector =
        new ActivityInjector(getLastNonConfigurationInstance(), this, getBaseInjector());
    activityInjector.inject(this);
  }

  protected Injector getBaseInjector() {
    return (Injector) getApplication();
  }

  public void prepareInstanceModules(List<Object> modules) {
  }

  public void preparePresenterModules(List<Object> modules) {
  }

  @SuppressWarnings("deprecation") @Override public Object onRetainNonConfigurationInstance() {
    return activityInjector.getNonConfigurationInstance();
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
