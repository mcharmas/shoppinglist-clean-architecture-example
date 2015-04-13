package pl.charmas.shoppinglist.base;

import android.app.ListActivity;
import android.os.Bundle;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.base.injectors.ActivityInjector;
import pl.charmas.shoppinglist.base.injectors.ModuleFactory;
import pl.charmas.shoppinglist.presentation.base.LifecycleNotifier;
import pl.charmas.shoppinglist.presentation.base.Presenter;
import pl.charmas.shoppinglist.presentation.base.UI;

public class BasePresenterListActivity<T extends UI> extends ListActivity implements ModuleFactory {
  @Inject LifecycleNotifier lifecycleNotifier;
  private Presenter<T> presenter;
  private T ui;
  private ActivityInjector activityInjector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityInjector = new ActivityInjector(this, getLastNonConfigurationInstance(), this);
    activityInjector.performInjection(this);
  }

  public void prepareInstanceModules(List<Object> modules) {
  }

  public void preparePresenterModules(List<Object> modules) {
    modules.add(new BasePresenterModule());
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
    lifecycleNotifier.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    lifecycleNotifier.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    lifecycleNotifier.onDestroy();
  }
}
