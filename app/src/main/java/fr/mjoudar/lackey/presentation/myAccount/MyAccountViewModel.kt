package fr.mjoudar.lackey.presentation.myAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.mjoudar.lackey.domain.models.User
import fr.mjoudar.lackey.repositories.DataRepository
import kotlinx.coroutines.launch

class MyAccountViewModel : ViewModel() {

    private val repository = DataRepository()

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    fun fetchUser() = viewModelScope.launch {
        //TODO: only Call if DataStorage is empty - otherwise, load from DataStorage
        val user = repository.getUser()
        _userLiveData.postValue(user)
    }

}