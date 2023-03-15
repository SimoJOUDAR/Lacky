package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var _firstName: String,
    var _lastName: String,
    var _address: Address,
    var _birthDate: Long
): Parcelable, BaseObservable() {

    @get:Bindable
    var firstName
        get() = _firstName
        set(value) {
            _firstName = value
        }

    @get:Bindable
    var lastName
        get() = _lastName
        set(value) {
            _lastName = value
        }

    @get:Bindable
    var address
        get() = _address
        set(value) {
            _address = value
        }

    @get:Bindable
    var birthDate
        get() = _birthDate
        set(value) {
            _birthDate = value
        }
}
