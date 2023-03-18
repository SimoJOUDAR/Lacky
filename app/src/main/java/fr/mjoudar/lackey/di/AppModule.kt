package fr.mjoudar.lackey.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.mjoudar.lackey.data.persistence.DataStoreManager
import fr.mjoudar.lackey.data.persistence.DataStoreManagerImpl
import javax.inject.Singleton

/***************************************************************************************************
 * This class is a hilt's module to handle dependency injection
 ***************************************************************************************************/
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // responsible for injecting a DataStoreManager instance singleton
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) : DataStoreManager = DataStoreManagerImpl(context)
}