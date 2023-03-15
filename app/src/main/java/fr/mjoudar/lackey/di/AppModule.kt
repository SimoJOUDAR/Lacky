package fr.mjoudar.lackey.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.mjoudar.lackey.persistence.DataStoreManager
import fr.mjoudar.lackey.persistence.DataStoreManagerImpl
import fr.mjoudar.lackey.repositories.DataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) : DataStoreManager = DataStoreManagerImpl(context)
}