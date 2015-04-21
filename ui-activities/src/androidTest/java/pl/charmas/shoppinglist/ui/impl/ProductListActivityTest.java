package pl.charmas.shoppinglist.ui.impl;

import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import dagger.Module;
import dagger.Provides;
import java.util.Arrays;
import java.util.Collections;
import javax.inject.Singleton;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.charmas.shoppinglist.presentation.ProductListPresenter;
import pl.charmas.shoppinglist.presentation.model.ProductViewModel;
import pl.charmas.shoppinglist.ui.base.TestActivityModules;
import pl.charmas.training.ui.ProductListActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ProductListActivityTest extends ActivityInstrumentationTestCase2<ProductListActivity> {

  @Mock
  ProductListPresenter presenter;
  private ProductListActivity activity;

  public ProductListActivityTest() {
    super(ProductListActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);
    TestActivityModules.setAdditionalTestModules(new TestModule(presenter));
    activity = getActivity();
  }

  @SuppressWarnings("unchecked")
  public void testShouldPresentProductList() throws Exception {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        activity.showProductList(
            Arrays.asList(new ProductViewModel(0, "Sample name", false), new ProductViewModel(0, "Sample name1", false)));
      }
    });
    onData(instanceOf(ProductViewModel.class))
        .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
        .atPosition(1)
        .check(matches(isDisplayed()));
  }

  @SuppressWarnings("unchecked")
  public void testShouldNotifyCallbackOnProductStatusChanged() throws Exception {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        activity.showProductList(Collections.singletonList(new ProductViewModel(0, "Sample name", false)));
      }
    });
    onView(allOf(ViewMatchers.isAssignableFrom(CheckBox.class), hasSibling(withText("Sample name")))).perform(click());
    Mockito.verify(presenter).productStatusChanged(Mockito.eq(0l), Mockito.eq(true));
  }

  public void testShouldNotifyCallbackWhenAddProducClicked() throws Exception {
    onView(withText("Add")).perform(click());
    Mockito.verify(presenter, Mockito.times(1)).onAddNewProduct();
  }

  @Module(overrides = true,
      injects = { ProductListActivityTest.class },
      complete = false,
      addsTo = ProductListActivity.PresentationModule.class)
  static class TestModule {

    private final ProductListPresenter presenter;

    public TestModule(ProductListPresenter presenter) {
      this.presenter = presenter;
    }

    @SuppressWarnings("unchecked")
    @Provides
    @Singleton ProductListPresenter provideTestPresenter() {
      return presenter;
    }
  }
}
