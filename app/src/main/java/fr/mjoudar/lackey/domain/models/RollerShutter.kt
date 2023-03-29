package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

/***************************************************************************************************
 * RollerShutter class - to represent RollerShutter device
 ***************************************************************************************************/
@Parcelize
data class RollerShutter(
    private var _id: Int,
    private var _deviceName: String,
    private var _position: Int
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
