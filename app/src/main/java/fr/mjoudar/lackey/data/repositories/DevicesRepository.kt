package fr.mjoudar.lackey.data.repositories

import fr.mjoudar.lackey.data.network.RetrofitInstance
import javax.inject.Inject

/***************************************************************************************************
 * DataRepository Class - a SSOT (Single Source of Truth) pattern to centralize the data access
 ***************************************************************************************************/
class DevicesRepository @Inject constructor () {

    // Retrieve a Response object from our Api
    suspend fun getDevicesState() = RetrofitInstance.apiClient.getData()
}