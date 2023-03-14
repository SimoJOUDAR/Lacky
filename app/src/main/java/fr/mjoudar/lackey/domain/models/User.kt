package fr.mjoudar.lackey.domain.models

data class User(
    var _firstName: String,
    var _lastName: String,
    var _address: Address,
    var _birthDate: Long
)
