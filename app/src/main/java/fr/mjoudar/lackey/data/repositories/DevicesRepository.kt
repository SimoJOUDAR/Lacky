package fr.mjoudar.lackey.data.repositories

import fr.mjoudar.lackey.data.network.ApiClient
import javax.inject.Inject

/***************************************************************************************************
 * DataRepository Class - a SSOT (Single Source of Truth) pattern to centralize the data access
 ***************************************************************************************************/
class DevicesRepository @Inject constructor (private val apiClient: ApiClient) {

    // Retrieve a Response object from our Api
    suspend fun getData() = apiClient.getData()
}