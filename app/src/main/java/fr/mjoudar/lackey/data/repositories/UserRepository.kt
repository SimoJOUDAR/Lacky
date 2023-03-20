package fr.mjoudar.lackey.data.repositories

import fr.mjoudar.lackey.data.network.RetrofitInstance
import fr.mjoudar.lackey.data.persistence.DataStoreManager
import fr.mjoudar.lackey.domain.models.User
import kotlinx.coroutines.flow.*

import javax.inject.Inject

class UserRepository @Inject constructor (
    private  val dataStoreManager: DataStoreManager
        ) {

    // Update the user in our local DataStorage
    suspend fun updateUser(user: User) {
        dataStoreManager.updateUser(user)
    }

    // Retrieve the user from the local DataStorage
    fun retrieveLocalUser() : Flow<User> {
        return dataStoreManager.retrieveUser()
    }

    // Retrieve the user from the Api
    suspend fun retrieveUserFromApi(): User? {

        val response = RetrofitInstance.apiClient.getData()

        if (response.failed || !response.isSuccessful) {
            return null
        }
        return response.body.toUser()
    }
}