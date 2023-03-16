package fr.mjoudar.lackey.repositories

import android.util.Log
import fr.mjoudar.lackey.domain.models.Device
import fr.mjoudar.lackey.domain.models.User
import fr.mjoudar.lackey.network.RetrofitInstance
import javax.inject.Inject

/***************************************************************************************************
 * DataRepository Class - a SSOT (Single Source of Truth) pattern to centralize the data access
 ***************************************************************************************************/
class DataRepository @Inject constructor () {

    private val TAG = "GetDataRepository"

    // Retrieve a List of Device objects from our Api
    suspend fun getDevices() : List<Device>? {

        val response = RetrofitInstance.apiClient.getData()

        if (response.failed || !response.isSuccessful) {
            Log.e(TAG, "Request getData() failed")
            return null
        }
        return response.body.toDevices()
    }

    // Retrieve a User object from our Api
    suspend fun getUser() : User? {

        val response = RetrofitInstance.apiClient.getData()

        if (response.failed || !response.isSuccessful) {
            Log.e(TAG, "Request getData() failed")
            return null
        }
        return response.body.toUser()
    }
}