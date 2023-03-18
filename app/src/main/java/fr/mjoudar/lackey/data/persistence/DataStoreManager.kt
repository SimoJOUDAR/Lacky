package fr.mjoudar.lackey.data.persistence

import fr.mjoudar.lackey.domain.models.User
import kotlinx.coroutines.flow.Flow

/***************************************************************************************************
 * DataStoreManager interface - to manage our Preferences DataStore operations
 ***************************************************************************************************/
interface DataStoreManager {

    // Persist or update User data into the local DataStore
    suspend fun updateUser(user: User)

    // Retrieve a User object from the local DataStore
    suspend fun retrieveUser(): Flow<User>

}