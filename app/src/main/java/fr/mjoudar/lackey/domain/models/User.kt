package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

/***************************************************************************************************
 * User class - to hold user information
 ***************************************************************************************************/
@Parcelize
data class User(
    private var _firstName: String,
    private var _lastName: String,
    private var _address: Address,
    private var _birthDate: Long
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
