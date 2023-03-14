package fr.mjoudar.lackey.presentation.deviceSteering

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.mjoudar.lackey.R
import fr.mjoudar.lackey.databinding.FragmentDeviceSteeringBinding
import fr.mjoudar.lackey.databinding.FragmentGridViewBinding
import fr.mjoudar.lackey.domain.models.Device
import fr.mjoudar.lackey.domain.models.Light
import fr.mjoudar.lackey.domain.models.ProductType

class DeviceSteeringFragment : Fragment() {

    private var _binding: FragmentDeviceSteeringBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDeviceSteeringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        // TODO: extract argument and convert it using when {}
        arguments?.let {
            val device = it.getParcelable<Device>(DEVICE_ARG)
            when (device!!.productType) {
                ProductType.Light-> {
                    binding.light = device.toLight()
                    binding.lightInfoViewer.viewLight.visibility = View.VISIBLE
                    binding.rollerShutterInfoViewer.viewRs.visibility = View.GONE
                    binding.heaterInfoViewer.viewHeater.visibility = View.GONE
                }
                ProductType.RollerShutter-> {
                    binding.rollerShutter = device.toRollerShutter()
                    binding.lightInfoViewer.viewLight.visibility = View.GONE
                    binding.rollerShutterInfoViewer.viewRs.visibility = View.VISIBLE
                    binding.heaterInfoViewer.viewHeater.visibility = View.GONE
                }
                else -> {
                    binding.heater = device.toHeater()
                    binding.lightInfoViewer.viewLight.visibility = View.GONE
                    binding.rollerShutterInfoViewer.viewRs.visibility = View.GONE
                    binding.heaterInfoViewer.viewHeater.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val DEVICE_ARG = "device"
    }

}