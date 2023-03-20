package fr.mjoudar.lackey.presentation.gridView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.lackey.domain.models.*
import fr.mjoudar.lackey.data.repositories.DevicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/***********************************************************************************************
 * HomePageViewModel class -
 * the ViewModel to handle fetching the Devices List, as well as the update and delete operations
 ***********************************************************************************************/
@HiltViewModel
class GridViewViewModel @Inject constructor(
    private val repository : DevicesRepository
): ViewModel() {

    private val _devicesUiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Loading)
    val devicesUiState = _devicesUiState.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = DeviceUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    init {
        getDevicesState()
    }

    // Update the deviceUiState based on the received Response
    private fun getDevicesState() = viewModelScope.launch(Dispatchers.IO) {
        val defaultException = Exception("An unidentified error occurred. We couldn't load the data. Please, check your internet connection.")
        val response = repository.getData()
        if (response.isSuccessful && response.body.toDevices() != null) {
            _devicesUiState.emit(DeviceUiState.Success(response.body.toDevices()!!))
        } else {
            val e = response.exception ?: defaultException
            _devicesUiState.emit(DeviceUiState.Error(e))
        }
    }

    // Update a Device inside the List
    fun updateDevice(device: Device) {
        if (devicesUiState.value is DeviceUiState.Success) {
            val newList = (devicesUiState.value as DeviceUiState.Success).devices.toMutableList()
            for (i in 0 until newList.size) if (newList[i].id == device.id) newList[i] = device
            viewModelScope.launch(Dispatchers.IO) {
                _devicesUiState.emit(DeviceUiState.Success(newList))
            }
        }
    }

    // Delete a Device from the List
    fun deleteDevice(id: Int) {
        if (devicesUiState.value is DeviceUiState.Success) {
            val newList = (devicesUiState.value as DeviceUiState.Success).devices.filterNot { it.id == id }
            viewModelScope.launch(Dispatchers.IO) {
                _devicesUiState.emit(DeviceUiState.Success(newList))
            }
        }
    }
}

sealed class DeviceUiState {
    object Loading: DeviceUiState()
    data class Success(val devices : List<Device>): DeviceUiState()
    data class Error(val error: Exception): DeviceUiState()
}