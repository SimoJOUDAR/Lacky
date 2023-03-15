package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Device(
    var _id: Int = 0,
    var _productType: ProductType,
    var _deviceName: String = "",
    var _intensity: Int = 0,
    var _mode: String = "OFF",
    var _position: Int = 0,
    var _temperature: Double = 0.0
) : Parcelable, BaseObservable() {

    @get:Bindable
    var id
        get() = _id
        set(value) {
            _id = value
        }

    @get:Bindable
    var productType
        get() = _productType
        set(value) {
            _productType = value
        }

    @get:Bindable
    var deviceName
        get() = _deviceName
        set(value) {
            _deviceName = value
        }

    @get:Bindable
    var intensity
        get() = _intensity
        set(value) {
            _intensity = value
        }

    @get:Bindable
    var mode
        get() = _mode
        set(value) {
            _mode = value
        }

    @get:Bindable
    var position
        get() = _position
        set(value) {
            _position = value
        }

    @get:Bindable
    var temperature
        get() = _temperature
        set(value) {
            _temperature = value
        }



    fun toLight() = Light(id, deviceName, intensity, Mode.valueOf(mode))
    fun toRollerShutter() = RollerShutter(id, deviceName, position)
    fun toHeater() = Heater(id, deviceName, temperature.toDouble(), Mode.valueOf(mode))
}

enum class ProductType {Light, RollerShutter, Heater}
