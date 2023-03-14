package fr.mjoudar.lackey.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

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
    }
}