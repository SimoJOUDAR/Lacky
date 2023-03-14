package fr.mjoudar.lackey.domain.dto


import com.squareup.moshi.Json
import fr.mjoudar.lackey.domain.models.*

/***********************************************************************************************
 ** GetDataResponse
 **********************************************************************************************/
data class GetDataResponse(@Json(name = "devices")
                           val devices: List<DevicesItem>?,
                           @Json(name = "user")
                           val user: User1) {

    fun toUser() = user.toUser()

    fun toDevices() = devices?.map { it.toDevice() }

}

/***********************************************************************************************
 ** User
 **********************************************************************************************/
data class User1(@Json(name = "firstName")
                val firstName: String = "",
                @Json(name = "lastName")
                val lastName: String = "",
                @Json(name = "address")
                val address: Address1,
                @Json(name = "birthDate")
                val birthDate: Long = 0) {

    fun toUser() = User(firstName, lastName, address.toAddress(), birthDate)
}

/***********************************************************************************************
 ** Address
 **********************************************************************************************/
data class Address1(@Json(name = "country")
                   val country: String = "",
                   @Json(name = "city")
                   val city: String = "",
                   @Json(name = "street")
                   val street: String = "",
                   @Json(name = "postalCode")
                   val postalCode: Int = 0,
                   @Json(name = "streetCode")
                   val streetCode: String = "") {

    fun toAddress() = Address(streetCode, street, city, postalCode, country)
}

/***********************************************************************************************
 ** DevicesItem
 **********************************************************************************************/
data class DevicesItem(@Json(name = "intensity")
                       val intensity: Int = 0,
                       @Json(name = "mode")
                       val mode: String = "OFF",
                       @Json(name = "id")
                       val id: Int = 0,
                       @Json(name = "deviceName")
                       val deviceName: String = "",
                       @Json(name = "productType")
                       val productType: String = "",
                       @Json(name = "position")
                       val position: Int = 0,
                       @Json(name = "temperature")
                       val temperature: Int = 0) {

    fun toDevice() = Device(id, ProductType.valueOf(productType), deviceName, intensity, mode, position, temperature.toDouble())
}


