package pl.charmas.training.ui;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.data.ProductsDataSourceModule;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;
import pl.charmas.shoppinglist.ui.base.async.AsyncUseCaseImpl;

@Module(includes = { ProductsDataSourceModule.class }, library = true)
public class AppModule {
  @Provides @Singleton AsyncUseCase provAsyncUseCase(AsyncUseCaseImpl asyncUseCase) {
    return asyncUseCase;
  }
}
