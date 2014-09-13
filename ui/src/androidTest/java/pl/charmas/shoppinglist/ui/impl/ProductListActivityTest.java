package pl.charmas.shoppinglist.ui.impl;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;

import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

import org.mockito.Mockito;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.shoppinglist.base.TestActivityModules;
import pl.charmas.shoppinglist.model.ProductViewModel;
import pl.charmas.shoppinglist.presenters.Presenter;
import pl.charmas.shoppinglist.ui.ProductListUI;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.hasSibling;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ProductListActivityTest extends ActivityInstrumentationTestCase2<ProductListActivity> {

    private ProductListActivity activity;

    public ProductListActivityTest() {
        super(ProductListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestActivityModules.setAdditionalTestModules(new TestModule());
        activity = getActivity();
        activity.getObjectGraph().inject(this);
    }

    @SuppressWarnings("unchecked")
    public void testShouldPresentProductList() throws Exception {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showProductList(Arrays.asList(new ProductViewModel(0, "Sample name", false), new ProductViewModel(0, "Sample name1", false)));
            }
        });
        onData(instanceOf(ProductViewModel.class))
                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                .atPosition(1)
                .check(matches(isDisplayed()));
    }

    @SuppressWarnings("unchecked")
    public void testShouldNotifyCallbackOnProductStatusChanged() throws Exception {
        ProductListUI.UICallbacks uiCallbacks = Mockito.mock(ProductListUI.UICallbacks.class);
        activity.setUICallbacks(uiCallbacks);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showProductList(Arrays.asList(new ProductViewModel(0, "Sample name", false)));
            }
        });
        onView(allOf(ViewMatchers.isAssignableFrom(CheckBox.class), hasSibling(withText("Sample name")))).perform(click());
        Mockito.verify(uiCallbacks).onProductStatusChanged(Mockito.eq(0l), Mockito.eq(true));
    }

    public void testShouldNotifyCallbackWhenAddProducClicked() throws Exception {
        ProductListUI.UICallbacks uiCallbacks = Mockito.mock(ProductListUI.UICallbacks.class);
        activity.setUICallbacks(uiCallbacks);

        onView(withText("Add")).perform(click());
        Mockito.verify(uiCallbacks, Mockito.times(1)).onAddNewProduct();
    }

    @Module(overrides = true,
            injects = {ProductListActivityTest.class},
            complete = false,
            addsTo = ProductListActivity.ProductListActivityModule.class)
    static class TestModule {

        @SuppressWarnings("unchecked")
        @Provides
        @Singleton
        Presenter<ProductListUI> provideTestPresenter() {
            return Mockito.mock(Presenter.class);
        }
    }

}