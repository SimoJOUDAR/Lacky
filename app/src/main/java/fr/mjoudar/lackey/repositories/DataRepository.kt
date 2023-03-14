package fr.mjoudar.lackey.repositories

import android.util.Log
import fr.mjoudar.lackey.domain.models.Device
import fr.mjoudar.lackey.domain.models.User
import fr.mjoudar.lackey.network.RetrofitInstance

class DataRepository {

    private val TAG = "GetDataRepository"

    suspend fun getDevices() : List<Device>? {

        val response = RetrofitInstance.apiClient.getData()

        if (response.failed || !response.isSuccessful) {
            Log.e(TAG, "Request getData() failed")
            return null
        }
        return response.body.toDevices()
    }

    suspend fun getUser() : User? {

        val response = RetrofitInstance.apiClient.getData()

        if (response.failed || !response.isSuccessful) {
            Log.e(TAG, "Request getData() failed")
            return null
        }
        return response.body.toUser()
    }
}