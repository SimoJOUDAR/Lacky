package fr.mjoudar.lackey.presentation.deviceSteering

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.mjoudar.lackey.databinding.FragmentDeviceSteeringBinding
import fr.mjoudar.lackey.domain.models.*
import fr.mjoudar.lackey.presentation.gridView.GridViewViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/***************************************************************************************************
 * DeviceSteeringFragment - the Fragment responsible of displaying device steering features
 ***************************************************************************************************/

@AndroidEntryPoint
class DeviceSteeringFragment : Fragment() {

    private var _binding: FragmentDeviceSteeringBinding? = null
    private val binding get() = _binding!!
    private val mHomepageViewModel: GridViewViewModel by activityViewModels()
    private val deviceSteeringViewModel: DeviceSteeringViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDeviceSteeringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setUpButtonListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**********************************************************************************************
     ** Display and data loading
     **********************************************************************************************/

    // Adapt the display accordingly to the Device received in the Fragment's Bundle
    private fun loadData() {
        arguments?.let { it1 ->
            binding.viewModel = deviceSteeringViewModel
            val device = it1.getParcelable<Device>(DEVICE_ARG)
            device?.let { it2 ->
                when (it2.productType) {

                    ProductType.Light-> {
                        displayLightSteering(it2.toLight())
                    }

                    ProductType.RollerShutter-> {
                        displayRollerShutterSteering(it2.toRollerShutter())
                    }

                    else -> {
                        displayHeaterSteering(it2.toHeater())
                    }
                }
            }
        }
    }

    // Adapt the display to fit Light Device features
    private fun displayLightSteering(data: Light) {
        binding.light = data
        deviceSteeringViewModel.setLight(data)
        setLightObservers()
        lightSeekbarListener()
        binding.lightInfoViewer.viewLight.visibility = View.VISIBLE
        binding.rollerShutterInfoViewer.viewRs.visibility = View.GONE
        binding.heaterInfoViewer.viewHeater.visibility = View.GONE
    }

    // Adapt the display to fit ShutterSteering Device features
    private fun displayRollerShutterSteering(data: RollerShutter) {
        binding.rollerShutter = data
        deviceSteeringViewModel.setRollerShutter(data)
        setRsObservers()
        rstSeekbarListener()
        binding.lightInfoViewer.viewLight.visibility = View.GONE
        binding.rollerShutterInfoViewer.viewRs.visibility = View.VISIBLE
        binding.heaterInfoViewer.viewHeater.visibility = View.GONE
    }

    // Adapt the display to fit Heater Device features
    private fun displayHeaterSteering(data: Heater) {
        binding.heater = data
        deviceSteeringViewModel.setHeater(data)
        setHeaterObservers()
        heaterSeekbarListener()
        binding.lightInfoViewer.viewLight.visibility = View.GONE
        binding.rollerShutterInfoViewer.viewRs.visibility = View.GONE
        binding.heaterInfoViewer.viewHeater.visibility = View.VISIBLE
    }

    /**********************************************************************************************
     ** Observers
     **********************************************************************************************/

    // Observe changes in DeviceSteeringViewModel's lightStateFlow to update the view's data
    private fun setLightObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                deviceSteeringViewModel.lightStateFlow.collectLatest {
                    Log.e("Test2", "setLightObservers() = $it")
                    it?.let {
                        binding.light = it
                        mHomepageViewModel.updateDevice(it.toDevice())
                    }
                }
            }
        }
    }

    // Observe changes in DeviceSteeringViewModel's rsStateFlow to update the view's data
    private fun setRsObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                deviceSteeringViewModel.rsStateFlow.collectLatest {
                    it?.let {
                        binding.rollerShutter = it.copy()
                        mHomepageViewModel.updateDevice(it.toDevice())
                    }
                }
            }
        }
    }

    // Observe changes in DeviceSteeringViewModel's heaterStateFlow to update the view's data
    private fun setHeaterObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                deviceSteeringViewModel.heaterStateFlow.collectLatest {
                    it?.let {
                        binding.heater = it.copy()
                        mHomepageViewModel.updateDevice(it.toDevice())
                    }
                }
            }
        }
    }


    /**********************************************************************************************
     ** Listeners
     **********************************************************************************************/

    // Listen to Light' seekbar changes and transmit it's progress value to the ViewModel's Light object
    private fun lightSeekbarListener() {
        binding.lightInfoViewer.progressBarLight.setOnClickListener {
            deviceSteeringViewModel.seekBarLightListener(binding.lightInfoViewer.progressBarLight.progress)
        }
    }

    // Listen to RollerShutter' seekbar changes and transmit it's progress value to the ViewModel's RollerShutter object
    private fun rstSeekbarListener() {
        binding.rollerShutterInfoViewer.progressBarRs.setOnClickListener {
            deviceSteeringViewModel.seekBarRSListener(binding.rollerShutterInfoViewer.progressBarRs.progress)
        }
    }

    // Listen to Heater' seekbar changes and transmit it's progress value to the ViewModel's Heater object
    private fun heaterSeekbarListener() {
        binding.heaterInfoViewer.progressBarHeater.setOnClickListener {
            deviceSteeringViewModel.seekBarHeaterListener(binding.heaterInfoViewer.progressBarHeater.progress)
        }
    }

    // Listen to click event of the Up button
    private fun setUpButtonListener() {
        binding.up.setOnClickListener {
            findNavController().navigate(DeviceSteeringFragmentDirections.actionDeviceSteeringFragmentToHomePageFragment())
        }
    }

    companion object {
        const val DEVICE_ARG = "device"
    }

}