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

    private val _devicesStateFlow = MutableStateFlow(listOf<Device>())
    val devicesStateFlow = _devicesStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    init {
        fetchDevices()
    }

    // Fetch the Devices List
    private fun fetchDevices() = viewModelScope.launch(Dispatchers.IO) {
        if (_devicesStateFlow.value.isEmpty()) {
            val devices = repository.getDevices()
            devices?.let { _devicesStateFlow.emit(it) }
        }
    }

    // Update a Device inside the List
    fun updateDevice(device: Device) {
        val newList = _devicesStateFlow.value.toMutableList()
        for (i in 0 until newList.size) if (newList[i].id == device.id) newList[i] = device
        viewModelScope.launch(Dispatchers.IO) {
            _devicesStateFlow.emit(newList)
        }
    }

    // Delete a Device from the List
    fun deleteDevice(id: Int) {
        val newList = _devicesStateFlow.value.filterNot { it.id == id }
        viewModelScope.launch(Dispatchers.IO) {
            _devicesStateFlow.emit(newList)
        }
    }
}