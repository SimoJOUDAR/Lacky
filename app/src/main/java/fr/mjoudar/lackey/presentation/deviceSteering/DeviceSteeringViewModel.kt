package fr.mjoudar.lackey.presentation.deviceSteering

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.lackey.domain.models.Heater
import fr.mjoudar.lackey.domain.models.Light
import fr.mjoudar.lackey.domain.models.Mode
import fr.mjoudar.lackey.domain.models.RollerShutter
import fr.mjoudar.lackey.utils.TemperatureCalculator
import javax.inject.Inject

@HiltViewModel
class DeviceSteeringViewModel @Inject constructor (): ViewModel() {

    /**********************************************************************************************
     * LiveData
     **********************************************************************************************/
    private val _lightLivedata = MutableLiveData<Light?>()
    val lightLivedata: LiveData<Light?> = _lightLivedata
    private val _rsLiveData = MutableLiveData<RollerShutter?>()
    val rsLiveData: LiveData<RollerShutter?> = _rsLiveData
    private val _heaterLiveData = MutableLiveData<Heater?>()
    val heaterLiveData: LiveData<Heater?> = _heaterLiveData

    private val _seekbarValue = MutableLiveData<Int?>()
    val seekbarValue: LiveData<Int?> = _seekbarValue

    /**********************************************************************************************
     * Setters
     **********************************************************************************************/

    fun setLight(light: Light) {
        _lightLivedata.postValue(light)
    }

    fun setRollerShutter(rs: RollerShutter) {
        _rsLiveData.postValue(rs)
    }

    fun setHeater(heater: Heater) {
        _heaterLiveData.postValue(heater)
    }

    /**********************************************************************************************
     * Light device OnClickListeners
     **********************************************************************************************/

    fun modeLightListener() {
        val light = lightLivedata.value
        light?.let {
            it.mode = switchOnOff(it.mode)
            _lightLivedata.postValue(it)
        }
    }

    fun buttonDecrLightListener() {
        val light = lightLivedata.value
        light?.let {
            if (it.intensity > STEP) it.intensity = it.intensity - STEP
            else it.intensity = PERCENTAGE_LOWER_LIMIT
            _lightLivedata.postValue(it)
        }
    }

    fun buttonIncrLightListener() {
        val light = lightLivedata.value
        light?.let {
            if (it.intensity < PERCENTAGE_UPPER_LIMIT - STEP) it.intensity = it.intensity + STEP
            else it.intensity = PERCENTAGE_UPPER_LIMIT
            _lightLivedata.postValue(it)
        }
    }

    fun seekBarLightListener(data: Int) {
        val light = lightLivedata.value
        light?.let {
            it.intensity = data
            _lightLivedata.postValue(it)
        }
    }

    /**********************************************************************************************
     * RollerShutter device OnClickListeners
     **********************************************************************************************/

    fun buttonDecrRSListener() {
        val rs = rsLiveData.value
        rs?.let {
            if (it.position > STEP) it.position = it.position - STEP
            else it.position = PERCENTAGE_LOWER_LIMIT
            _rsLiveData.postValue(it)
        }
    }

    fun buttonIncrRStListener() {
        val rs = rsLiveData.value
        rs?.let {
            if (it.position < PERCENTAGE_UPPER_LIMIT - STEP) it.position = it.position + STEP
            else it.position = PERCENTAGE_UPPER_LIMIT
            _rsLiveData.postValue(it)
        }
    }

    fun seekBarRSListener(data: Int) {
        val rs = rsLiveData.value
        rs?.let {
            it.position = data
            _rsLiveData.postValue(it)
        }
    }

    /**********************************************************************************************
     * Heater device OnClickListeners
     **********************************************************************************************/

    fun modeHeaterListener() {
        val heater = heaterLiveData.value
        heater?.let {
            it.mode = switchOnOff(it.mode)
            _heaterLiveData.postValue(it)
        }
    }

    fun buttonDecrHeaterListener() {
        val heater = heaterLiveData.value
        heater?.let {
            if (it.temperature > TEMPERATURE_LOWER_LIMIT + TEMPERATURE_STEP)
                it.temperature = it.temperature - TEMPERATURE_STEP
            else
                it.temperature = TEMPERATURE_LOWER_LIMIT
            _heaterLiveData.postValue(it)
        }
    }

    fun buttonIncrHeatertListener() {
        val heater = heaterLiveData.value
        heater?.let {
            if (it.temperature < TEMPERATURE_UPPER_LIMIT - TEMPERATURE_STEP)
                it.temperature = it.temperature + TEMPERATURE_STEP
            else
                it.temperature = TEMPERATURE_UPPER_LIMIT
            _heaterLiveData.postValue(it)
        }
    }

    fun seekBarHeaterListener(data: Int) {
        val heater = heaterLiveData.value
        heater?.let {
            it.temperature = TemperatureCalculator.fromPercentToTemperature(data)
            _heaterLiveData.postValue(it)
        }
    }

    /**********************************************************************************************
     * Utils
     **********************************************************************************************/

    private fun switchOnOff(mode: Mode) : Mode {
        return if (mode == Mode.OFF) Mode.ON else Mode.OFF
    }

    companion object {
        const val DEVICE_ARG = "device"
        const val STEP = 10
        const val PERCENTAGE_UPPER_LIMIT = 100
        const val PERCENTAGE_LOWER_LIMIT = 0

        const val TEMPERATURE_STEP = 0.5
        const val TEMPERATURE_UPPER_LIMIT = 28.0
        const val TEMPERATURE_LOWER_LIMIT = 7.0

    }
    /**********************************************************************************************
     *
     **********************************************************************************************/
}
