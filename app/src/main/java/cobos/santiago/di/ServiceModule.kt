package cobos.santiago.di

import cobos.santiago.data.remote.Auth
import cobos.santiago.data.remote.MusicDatabase
import cobos.santiago.ui.viewmodels.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideMusicDatabase(): MusicDatabase {
        return MusicDatabase()
    }

    @ServiceScoped
    @Provides
    fun provideAuth(userViewModel: UserViewModel): Auth {
        return Auth(userViewModel)
    }
}
