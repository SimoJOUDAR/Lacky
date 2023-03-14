package fr.mjoudar.lackey.presentation.deviceSteering

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import fr.mjoudar.lackey.databinding.FragmentDeviceSteeringBinding
import fr.mjoudar.lackey.domain.models.Device
import fr.mjoudar.lackey.domain.models.Mode
import fr.mjoudar.lackey.domain.models.ProductType
import fr.mjoudar.lackey.presentation.homePage.HomePageViewModel

class DeviceSteeringFragment : Fragment() {

    private var _binding: FragmentDeviceSteeringBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomePageViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDeviceSteeringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupListener()
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

    private fun setupListener() {
        binding.light?.let { setupLightListeners() }
        binding.rollerShutter?.let { setupRollerShutterListener() }
        binding.heater?.let { setupHeaterListener() }
    }

    private fun setupLightListeners() {
        binding.lightInfoViewer.modeLight.setOnClickListener {
            val light = binding.light!!
            light.mode = switchOnOff(light.mode)
            binding.light = light
            viewModel.updateDevice(light.toDevice())
        }
        binding.lightInfoViewer.buttonDecrLight.setOnClickListener {
            val light = binding.light!!
            if (light.intensity > STEP) light.intensity = light.intensity - STEP
            else light.intensity = PERCENTAGE_LOWER_LIMIT
            binding.light = light
            viewModel.updateDevice(light.toDevice())
        }
        binding.lightInfoViewer.buttonIncrLight.setOnClickListener {
            val light = binding.light!!
            if (light.intensity < PERCENTAGE_UPPER_LIMIT - STEP) light.intensity = light.intensity + STEP
            else light.intensity = PERCENTAGE_UPPER_LIMIT
            binding.light = light
            viewModel.updateDevice(light.toDevice())
        }
    }

    private fun setupRollerShutterListener() {
        binding.rollerShutterInfoViewer.buttonDecrRs.setOnClickListener {
            val rollerShutter = binding.rollerShutter!!
            if (rollerShutter.position > STEP) rollerShutter.position = rollerShutter.position - STEP
            else rollerShutter.position = PERCENTAGE_LOWER_LIMIT
            binding.rollerShutter = rollerShutter
            viewModel.updateDevice(rollerShutter.toDevice())
        }
        binding.rollerShutterInfoViewer.buttonIncrRs.setOnClickListener {
            val rollerShutter = binding.rollerShutter!!
            if (rollerShutter.position < PERCENTAGE_UPPER_LIMIT - STEP) rollerShutter.position = rollerShutter.position + STEP
            else rollerShutter.position = PERCENTAGE_UPPER_LIMIT
            binding.rollerShutter = rollerShutter
            viewModel.updateDevice(rollerShutter.toDevice())
        }
    }

    private fun setupHeaterListener() {
        binding.heaterInfoViewer.modeHeater.setOnClickListener {
            val heater = binding.heater!!
            heater.mode = switchOnOff(heater.mode)
            binding.heater = heater
            viewModel.updateDevice(heater.toDevice())
        }
        binding.heaterInfoViewer.buttonDecrHeater.setOnClickListener { //TODO : change 10% to 0.5°
            val heater = binding.heater!!
            if (heater.temperature > TEMPERATURE_LOWER_LIMIT + TEMPERATURE_STEP)
                heater.temperature = heater.temperature - TEMPERATURE_STEP
            else
                heater.temperature = TEMPERATURE_LOWER_LIMIT
            binding.heater = heater
            viewModel.updateDevice(heater.toDevice())
        }
        binding.heaterInfoViewer.buttonIncrHeater.setOnClickListener {
            val heater = binding.heater!!
            if (heater.temperature < TEMPERATURE_UPPER_LIMIT - TEMPERATURE_STEP)
                heater.temperature = heater.temperature + TEMPERATURE_STEP
            else
                heater.temperature = TEMPERATURE_UPPER_LIMIT
            binding.heater = heater
            viewModel.updateDevice(heater.toDevice())
        }

    }

    private fun switchOnOff(mode: Mode) : Mode {
        return if (mode == Mode.OFF) Mode.ON else Mode.OFF
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val DEVICE_ARG = "device"
        const val STEP = 10
        const val PERCENTAGE_UPPER_LIMIT = 100
        const val PERCENTAGE_LOWER_LIMIT = 0

        const val TEMPERATURE_STEP = 0.5
        const val TEMPERATURE_UPPER_LIMIT = 28.0
        const val TEMPERATURE_LOWER_LIMIT = 7.0

    }

}