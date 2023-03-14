package fr.mjoudar.lackey.domain.models

data class Address(
    var _streetCode: String,
    var _street: String,
    var _city: String,
    var _zipCode: Int,
    var _country: String
)
