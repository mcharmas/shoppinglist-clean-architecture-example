package pl.charmas.shoppinglist.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.shoppinglist.presentation.base.LifecycleNotifier;
import pl.charmas.shoppinglist.presentation.base.Presenter;
import pl.charmas.shoppinglist.presentation.base.UI;

public abstract class BasePresenterActivity<T extends UI> extends FragmentActivity implements ActivityInjector.ModuleFactory {
  @Inject LifecycleNotifier lifecycleNotifier;
  private Presenter<T> presenter;
  private T ui;
  private ActivityInjector activityInjector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityInjector = new ActivityInjector(this, getLastCustomNonConfigurationInstance(), this);
    activityInjector.performInjection(this);
  }

  public void prepareAdditionalActivityModules(List<Object> modules) {
  }

  public void prepareAdditionalPresenterModules(List<Object> modules) {
    modules.add(new BasePresenterModule());
  }

  @Override public Object onRetainCustomNonConfigurationInstance() {
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
