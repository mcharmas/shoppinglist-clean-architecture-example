package pl.charmas.shoppinglist.app;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.shoppinglist.async.AsyncUseCaseImpl;
import pl.charmas.shoppinglist.data.ProductsDataSourceModule;
import pl.charmas.shoppinglist.presentation.async.AsyncUseCase;

@Module(includes = { ProductsDataSourceModule.class }, library = true)
public class AppModule {
  @Provides @Singleton AsyncUseCase provAsyncUseCase(AsyncUseCaseImpl asyncUseCase) {
    return asyncUseCase;
  }
}
