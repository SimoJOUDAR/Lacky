package fr.mjoudar.lackey.persistence

import fr.mjoudar.lackey.domain.models.User
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    suspend fun updateUser(user: User)

    suspend fun retrieveUser(): Flow<User>

}