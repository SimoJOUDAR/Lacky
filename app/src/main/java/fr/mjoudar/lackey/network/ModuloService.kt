package fr.mjoudar.lackey.network

import fr.mjoudar.lackey.domain.dto.GetDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface ModuloService {

    @GET("modulotest/data.json")
    suspend fun getData(): Response<GetDataResponse>

}