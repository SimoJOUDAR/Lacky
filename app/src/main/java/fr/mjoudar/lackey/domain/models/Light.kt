package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

/***************************************************************************************************
 * Light class - to represent Light device
 ***************************************************************************************************/
@Parcelize
data class Light(
    private var _id: Int,
    private var _deviceName: String,
    private var _intensity: Int,
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

    fun toDevice() = Device(id, ProductType.Light, deviceName, intensity, mode.name)

}

enum class Mode {ON, OFF}
