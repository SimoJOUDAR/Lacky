package fr.mjoudar.lackey.presentation.deviceSteering

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.lackey.domain.models.Heater
import fr.mjoudar.lackey.domain.models.Light
import fr.mjoudar.lackey.domain.models.Mode
import fr.mjoudar.lackey.domain.models.RollerShutter
import fr.mjoudar.lackey.utils.TemperatureCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/***************************************************************************************************
 * DeviceSteeringViewModel class - the ViewModel to handle steering operation and data
 ***************************************************************************************************/

@HiltViewModel
class DeviceSteeringViewModel @Inject constructor (): ViewModel() {

    /**********************************************************************************************
     ** StateFlow
     **********************************************************************************************/
    private val _lightStateFlow = MutableStateFlow<Light?>(null)
    val lightStateFlow = _lightStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    private val _rsStateFlow = MutableStateFlow<RollerShutter?>(null)
    val rsStateFlow = _rsStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    private val _heaterStateFlow = MutableStateFlow<Heater?>(null)
    val heaterStateFlow = _heaterStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    /**********************************************************************************************
     ** Setters
     **********************************************************************************************/

    fun setLight(light: Light) {
        viewModelScope.launch(Dispatchers.IO) {
            _lightStateFlow.emit(light)
        }
    }

    fun setRollerShutter(rs: RollerShutter) {
        viewModelScope.launch(Dispatchers.IO) {
            _rsStateFlow.emit(rs)
        }
    }

    fun setHeater(heater: Heater) {
        viewModelScope.launch(Dispatchers.IO) {
            _heaterStateFlow.emit(heater)
        }
    }

    /**********************************************************************************************
     ** Light device OnClickListeners
     **********************************************************************************************/

    // Handle Light's ON/OFF operations
    fun modeLightListener() {
        val light = lightStateFlow.value?.copy()
        light?.let {
            it.mode = switchOnOff(it.mode)
            viewModelScope.launch(Dispatchers.IO) {
                _lightStateFlow.emit(it)
            }

        }
    }

    // Decrease Light intensity by 10%
    fun buttonDecrLightListener() {
        val light = lightStateFlow.value?.copy()
        light?.let {
            if (it.intensity > STEP) it.intensity = it.intensity - STEP
            else it.intensity = PERCENTAGE_LOWER_LIMIT
            viewModelScope.launch(Dispatchers.IO) {
                _lightStateFlow.emit(it)
            }
        }
    }

    // Increase Light intensity by 10%
    fun buttonIncrLightListener() {
        val light = lightStateFlow.value?.copy()
        light?.let {
            if (it.intensity < PERCENTAGE_UPPER_LIMIT - STEP) it.intensity = it.intensity + STEP
            else it.intensity = PERCENTAGE_UPPER_LIMIT
            viewModelScope.launch(Dispatchers.IO) {
                _lightStateFlow.emit(it)
            }
        }
    }

    // Set the Light's intensity to a specific value - the progress bar's value
    fun seekBarLightListener(data: Int) {
        val light = lightStateFlow.value?.copy()
        light?.let {
            it.intensity = data
            viewModelScope.launch(Dispatchers.IO) {
                _lightStateFlow.emit(it)
            }
        }
    }

    /**********************************************************************************************
     ** RollerShutter device OnClickListeners
     **********************************************************************************************/

    // Decrease RollerShutter position by 10%
    fun buttonDecrRSListener() {
        val rs = rsStateFlow.value?.copy()
        rs?.let {
            if (it.position > STEP) it.position = it.position - STEP
            else it.position = PERCENTAGE_LOWER_LIMIT
            viewModelScope.launch(Dispatchers.IO) {
                _rsStateFlow.emit(it)
            }
        }
    }

    // Increase RollerShutter position by 10%
    fun buttonIncrRStListener() {
        val rs = rsStateFlow.value?.copy()
        rs?.let {
            if (it.position < PERCENTAGE_UPPER_LIMIT - STEP) it.position = it.position + STEP
            else it.position = PERCENTAGE_UPPER_LIMIT
            viewModelScope.launch(Dispatchers.IO) {
                _rsStateFlow.emit(it)
            }
        }
    }

    // Set RollerShutter position to a specific value - the progress bar's value
    fun seekBarRSListener(data: Int) {
        val rs = rsStateFlow.value?.copy()
        rs?.let {
            it.position = data
            viewModelScope.launch(Dispatchers.IO) {
                _rsStateFlow.emit(it)
            }
        }
    }

    /**********************************************************************************************
     ** Heater device OnClickListeners
     **********************************************************************************************/

    // Handle Heater's ON/OFF operations
    fun modeHeaterListener() {
        val heater = heaterStateFlow.value?.copy()
        heater?.let {
            it.mode = switchOnOff(it.mode)
            viewModelScope.launch(Dispatchers.IO) {
                _heaterStateFlow.emit(it)
            }
        }
    }

    // Decrease Heater temperature by 0.5°
    fun buttonDecrHeaterListener() {
        val heater = heaterStateFlow.value?.copy()
        heater?.let {
            if (it.temperature > TEMPERATURE_LOWER_LIMIT + TEMPERATURE_STEP)
                it.temperature = it.temperature - TEMPERATURE_STEP
            else
                it.temperature = TEMPERATURE_LOWER_LIMIT
            viewModelScope.launch(Dispatchers.IO) {
                _heaterStateFlow.emit(it)
            }
        }
    }

    // Increase Heater temperature by 0.5°
    fun buttonIncrHeatertListener() {
        val heater = heaterStateFlow.value?.copy()
        heater?.let {
            if (it.temperature < TEMPERATURE_UPPER_LIMIT - TEMPERATURE_STEP)
                it.temperature = it.temperature + TEMPERATURE_STEP
            else
                it.temperature = TEMPERATURE_UPPER_LIMIT
            viewModelScope.launch(Dispatchers.IO) {
                _heaterStateFlow.emit(it)
            }
        }
    }

    // Set the Heater's temperature to a specific value - the progress bar's value
    fun seekBarHeaterListener(data: Int) {
        val heater = heaterStateFlow.value?.copy()
        heater?.let {
            it.temperature = TemperatureCalculator.fromPercentToTemperature(data)
            viewModelScope.launch(Dispatchers.IO) {
                _heaterStateFlow.emit(it)
            }
        }
    }

    /**********************************************************************************************
     ** Utils
     **********************************************************************************************/

    // Switch the ON/OFF value
    private fun switchOnOff(mode: Mode) : Mode {
        return if (mode == Mode.OFF) Mode.ON else Mode.OFF
    }

    companion object {
        const val STEP = 10
        const val PERCENTAGE_UPPER_LIMIT = 100
        const val PERCENTAGE_LOWER_LIMIT = 0

        const val TEMPERATURE_STEP = 0.5
        const val TEMPERATURE_UPPER_LIMIT = 28.0
        const val TEMPERATURE_LOWER_LIMIT = 7.0

    }
}
