package fr.mjoudar.lackey.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.rtugeek.android.colorseekbar.ColorSeekBar
import fr.mjoudar.lackey.domain.models.User
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/***********************************************************************************************
 * CustomViewBinding class - to extend data binding features of our Layouts
 ***********************************************************************************************/
class CustomViewBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("intensity")
        fun bindGetIntensity(textView: TextView, intensity: Int?) {
            intensity?.let {
                textView.text = it.toString().plus("%")
            }
        }

        @JvmStatic
        @BindingAdapter("temperature")
        fun bindGetTemperature(textView: TextView, temperature: Double?) {
            temperature?.let {
                textView.text = it.toString().plus("°")
            }
        }

        @JvmStatic
        @BindingAdapter("fullName")
        fun bindUserFullName(textView: TextView, user: User?) {
            user?.let {
                textView.text = it.firstName.plus(" ").plus(it.lastName)
            }
        }

        @JvmStatic
        @BindingAdapter("birthDate")
        fun bindUserBirthDate(textView: TextView, user: User?) {
            user?.let {
                val date = Date(it.birthDate)
                val format = SimpleDateFormat("dd/MM/yyyy")
                textView.text = format.format(date)
            }
        }

        @JvmStatic
        @BindingAdapter("addressLine1")
        fun bindAddressLine1(textView: TextView, user: User?) {
            user?.let {
                textView.text = it.address.streetCode.plus(" ").plus(it.address.street)
            }
        }

        @JvmStatic
        @BindingAdapter("addressLine2")
        fun bindAddressLine2(textView: TextView, user: User?) {
            user?.let {
                textView.text = it.address.zipCode.toString().plus(" ").plus(it.address.city)
            }
        }

        @JvmStatic
        @BindingAdapter("addressLine3")
        fun bindAddressLine3(textView: TextView, user: User?) {
            user?.let {
                textView.text = it.address.country
            }
        }

        @JvmStatic
        @BindingAdapter("zipCode")
        fun bindZipCode(textView: TextView, zipCode: Int?) {
            zipCode?.let {
                textView.text = it.toString()
            }
        }

        @JvmStatic
        @BindingAdapter("temperatureLevel")
        fun convertTemperature(seekbar: ColorSeekBar, temperature: Double?) {
            temperature?.let {
                seekbar.progress = TemperatureCalculator.fromTemperatureToPercent(it)
            }
        }
    }
}