package fr.mjoudar.lackey.data.network

import fr.mjoudar.lackey.domain.dto.GetDataResponse
import retrofit2.Response
import retrofit2.http.GET

/***************************************************************************************************
 * ModuloService interface - to handle our HTTP Api
 ***************************************************************************************************/
interface ModuloService {

    @GET("modulotest/data.json")
    suspend fun getData(): Response<GetDataResponse>

}