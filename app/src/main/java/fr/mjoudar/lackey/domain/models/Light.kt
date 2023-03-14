package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Light(
    var _id: Int,
    var _deviceName: String,
    var _intensity: Int,
    var _mode: Mode
) : Parcelable, BaseObservable() {

    @get:Bindable
    var id
        get() = _id
        set(value) {
            _id = value
//            notifyPropertyChanged(BR.id)
        }

    @get:Bindable
    var deviceName
        get() = _deviceName
        set(value) {
            _deviceName = value
//            notifyPropertyChanged(BR.deviceName)
        }

    @get:Bindable
    var intensity
        get() = _intensity
        set(value) {
            _intensity = value
//            notifyPropertyChanged(BR.intensity)
        }

    @get:Bindable
    var mode
        get() = _mode
        set(value) {
            _mode = value
//            notifyPropertyChanged(BR.mode)
        }

    fun toDevice() = Device(id, ProductType.Light, deviceName, intensity, mode.name)

}

enum class Mode {ON, OFF}
