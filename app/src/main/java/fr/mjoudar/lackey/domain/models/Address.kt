package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var _streetCode: String,
    var _street: String,
    var _city: String,
    var _zipCode: Int,
    var _country: String
) : Parcelable, BaseObservable() {

    @get:Bindable
    var streetCode
        get() = _streetCode
        set(value) {
        _streetCode = value
        }

    @get:Bindable
    var street
        get() = _street
        set(value) {
            _street = value
        }

    @get:Bindable
    var city
        get() = _city
        set(value) {
            _city = value
        }

    @get:Bindable
    var zipCode
        get() = _zipCode
        set(value) {
            _zipCode = value
        }

    @get:Bindable
    var country
        get() = _country
        set(value) {
            _country = value
        }
}
