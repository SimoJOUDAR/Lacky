package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

/***************************************************************************************************
 * Heater class - to represent Heater device
 ***************************************************************************************************/
@Parcelize
data class Heater(
    private var _id: Int,
    private var _deviceName: String,
    private var _temperature: Double,
    private var _mode: Mode
) : Parcelable, BaseObservable() {

    @get:Bindable
    var id
        get() = _id
        set(value) {
            _id = value
        }

    @get:Bindable
    var deviceName
        get() = _deviceName
        set(value) {
            _deviceName = value
        }

    @get:Bindable
    var temperature
        get() = _temperature
        set(value) {
            _temperature = value
        }

    @get:Bindable
    var mode
        get() = _mode
        set(value) {
            _mode = value
        }

    fun toDevice() = Device(id, ProductType.Heater, deviceName, 0, mode.name, 0, temperature)
}
