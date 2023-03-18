package fr.mjoudar.lackey.presentation.myAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.lackey.domain.models.User
import fr.mjoudar.lackey.data.persistence.DataStoreManager
import fr.mjoudar.lackey.data.repositories.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/***********************************************************************************************
 * MyAccountViewModel class - the ViewModel to handle retrieving and updating the user
 ***********************************************************************************************/
@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private  val dataStoreManager: DataStoreManager
    ): ViewModel() {

    private val repository = DataRepository()

    private val _userLiveData : MutableLiveData<User> = MutableLiveData()
    val userLiveData: LiveData<User> = _userLiveData

    // Update the User
    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _userLiveData.postValue(user)
            dataStoreManager.updateUser(user)
        }
    }

    // Retrieve the user from the local DataStorage, if is exists, otherwise fetch it from the network
    fun retrieveUser(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.retrieveUser().collect {
                if (it.firstName.isEmpty() && it.lastName.isEmpty()) fetchUser(it)
                else _userLiveData.postValue(it)
            }
        }
    }

    // Fetch the user from the network
    private fun fetchUser(emptyUser: User) = viewModelScope.launch {
        val user = repository.getUser()
        user?.let { _userLiveData.postValue(it) } ?: _userLiveData.postValue(emptyUser)
        // TODO: Persist the user now ?
    }
}