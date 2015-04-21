package pl.charmas.training.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.ProductsDataSourceModule;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;
import pl.charmas.shoppinglist.ui.base.async.AsyncUseCaseImpl;

@Module(includes = { ProductsDataSourceModule.class }, library = true)
public class AppModule {
  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton AsyncUseCase provAsyncUseCase(AsyncUseCaseImpl asyncUseCase) {
    return asyncUseCase;
  }

  @Provides @Singleton SharedPreferences provideSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
