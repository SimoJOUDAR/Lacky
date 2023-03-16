package fr.mjoudar.lackey.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/***************************************************************************************************
 * RetrofitInstance object - to generate an implementation of our ModuloService interface
 ***************************************************************************************************/
object RetrofitInstance {

    private const val BASE_URL = "http://storage42.com/"

    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val moduloService: ModuloService by lazy {
        retrofit.create(ModuloService::class.java)
    }

    val apiClient = ApiClient(moduloService)

    val api: ModuloService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ModuloService::class.java)
    }
}
