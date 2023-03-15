package fr.mjoudar.lackey.presentation.myAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.lackey.domain.models.User
import fr.mjoudar.lackey.persistence.DataStoreManager
import fr.mjoudar.lackey.repositories.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private  val dataStoreManager: DataStoreManager
    ): ViewModel() {

    private val repository = DataRepository()

    private val _userLiveData : MutableLiveData<User> = MutableLiveData()
    val userLiveData: LiveData<User> = _userLiveData

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _userLiveData.postValue(user)
            dataStoreManager.updateUser(user)
        }
    }

    fun retrieveUser(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.retrieveUser().collect {
                if (it.firstName.isEmpty() && it.lastName.isEmpty()) fetchUser(it)
                else _userLiveData.postValue(it)
            }
        }
    }

    private fun fetchUser(emptyUser: User) = viewModelScope.launch {
        val user = repository.getUser()
        user?.let { _userLiveData.postValue(it) } ?: _userLiveData.postValue(emptyUser)
    }
}