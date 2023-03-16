package fr.mjoudar.lackey.network

import fr.mjoudar.lackey.domain.dto.GetDataResponse
import retrofit2.Response

/***************************************************************************************************
 * ApiClient class - to handle Api calls
 ***************************************************************************************************/
class ApiClient (private val moduloService : ModuloService) {

    suspend fun getData() : SimpleResponse<GetDataResponse> {
        return safeApiCall { moduloService.getData() }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}