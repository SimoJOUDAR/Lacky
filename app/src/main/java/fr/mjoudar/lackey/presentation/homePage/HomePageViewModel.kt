package fr.mjoudar.lackey.presentation.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.lackey.domain.models.*
import fr.mjoudar.lackey.presentation.deviceSteering.DeviceSteeringFragment
import fr.mjoudar.lackey.repositories.DataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository : DataRepository
): ViewModel() {

    private val _devicesLiveData = MutableLiveData(listOf<Device>())
    val devicesLiveData: LiveData<List<Device>> = _devicesLiveData

    fun fetchDevices() = viewModelScope.launch {
        if (_devicesLiveData.value!!.isEmpty()) {
            val devices = repository.getDevices()
            _devicesLiveData.postValue(devices)
        }
    }

    fun updateDevice(device: Device) {
        val newList = _devicesLiveData.value!!.toMutableList()
        for (i in 0 until newList.size) if (newList[i].id == device.id) newList[i] = device
        _devicesLiveData.postValue(newList)
    }

    fun deleteDevice(id: Int) {
        val newList = _devicesLiveData.value!!.filterNot { it.id == id }
        _devicesLiveData.postValue(newList)
    }
}