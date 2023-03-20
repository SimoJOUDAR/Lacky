package fr.mjoudar.lackey.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.mjoudar.lackey.data.network.ApiClient
import fr.mjoudar.lackey.data.network.ModuloService
import fr.mjoudar.lackey.data.persistence.DataStoreManager
import fr.mjoudar.lackey.data.persistence.DataStoreManagerImpl
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/***************************************************************************************************
 * This class is a hilt's module to handle dependency injection
 ***************************************************************************************************/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "http://storage42.com/"

    // Responsible for injecting a DataStoreManager instance singleton
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) : DataStoreManager = DataStoreManagerImpl(context)



    // Responsible for injecting a Retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Responsible for injecting a ModuloService instance
    @Singleton
    @Provides
    fun provideModuloService(retrofit: Retrofit): ModuloService = retrofit.create(ModuloService::class.java)

    // Responsible for injecting an ApiClient instance
    @Singleton
    @Provides
    fun provideApiClient(apiService: ModuloService) = ApiClient(apiService)

}