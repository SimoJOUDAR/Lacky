package fr.mjoudar.lackey.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Device(val id: Int = 0,
                  val productType: ProductType,
                  val deviceName: String = "",
                  val intensity: Int = 0,
                  val mode: String = "OFF",
                  val position: Int = 0,
                  val temperature: Double = 0.0) : Parcelable {
    fun toLight() = Light(id, deviceName, intensity, Mode.valueOf(mode))
    fun toRollerShutter() = RollerShutter(id, deviceName, position)
    fun toHeater() = Heater(id, deviceName, temperature.toDouble(), Mode.valueOf(mode))
}

enum class ProductType {Light, RollerShutter, Heater}
