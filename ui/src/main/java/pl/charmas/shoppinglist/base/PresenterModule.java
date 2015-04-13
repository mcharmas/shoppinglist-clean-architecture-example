package pl.charmas.shoppinglist.base;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.presentation.base.LifecycleNotifier;

@Module(complete = false, library = true) class PresenterModule {
  @Provides @Singleton LifecycleNotifier provideLifecycleNotifier() {
    return new LifecycleNotifierImpl();
  }
}
