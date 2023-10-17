package cl.neoxcore.brewdogproject.ui.di

import cl.neoxcore.brewdogproject.data.Local
import cl.neoxcore.brewdogproject.data.Remote
import cl.neoxcore.brewdogproject.data.RepositoryImpl
import cl.neoxcore.brewdogproject.data.local.LocalImpl
import cl.neoxcore.brewdogproject.data.remote.RemoteImpl
import cl.neoxcore.brewdogproject.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(data: RepositoryImpl): Repository {
        return data
    }

    @Singleton
    @Provides
    fun provideRemote(dataSourceRemote: RemoteImpl): Remote {
        return dataSourceRemote
    }

    @Singleton
    @Provides
    fun provideLocal(dataSourceLocal: LocalImpl): Local {
        return dataSourceLocal
    }

}
