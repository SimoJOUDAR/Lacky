package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RollerShutter(
    var _id: Int,
    var _deviceName: String,
    var _position: Int
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
    var position
        get() = _position
        set(value) {
            _position = value
        }

    fun toDevice() = Device(id, ProductType.RollerShutter, deviceName, 0, "OFF", position)
}
