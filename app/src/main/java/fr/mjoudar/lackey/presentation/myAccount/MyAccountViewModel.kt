package fr.mjoudar.lackey.presentation.myAccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.lackey.domain.models.User
import fr.mjoudar.lackey.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/***********************************************************************************************
 * MyAccountViewModel class - the ViewModel to handle retrieving and updating the user
 ***********************************************************************************************/
@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val repository : UserRepository
    ): ViewModel() {

    private val _userStateFlow : MutableStateFlow<User?> = MutableStateFlow(null)
    val userStateFlow = _userStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    init {
        retrieveUser()
    }

    // Update the User
    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    // Retrieve the user from the local DataStorage, if is exists, otherwise fetch it from the network
    private fun retrieveUser(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.retrieveLocalUser().collectLatest {
                if (it.firstName.isEmpty() && it.lastName.isEmpty()) _userStateFlow.value = repository.retrieveUserFromApi()
                else _userStateFlow.emit(it)
            }
        }
    }
}