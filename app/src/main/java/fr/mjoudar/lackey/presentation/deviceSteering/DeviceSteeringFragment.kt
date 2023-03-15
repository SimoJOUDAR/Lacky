package fr.mjoudar.lackey.presentation.deviceSteering

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.mjoudar.lackey.databinding.FragmentDeviceSteeringBinding
import fr.mjoudar.lackey.domain.models.Device
import fr.mjoudar.lackey.domain.models.ProductType
import fr.mjoudar.lackey.presentation.homePage.HomePageViewModel
import fr.mjoudar.lackey.presentation.myAccount.MyAccountFragmentDirections

@AndroidEntryPoint
class DeviceSteeringFragment : Fragment() {

    private var _binding: FragmentDeviceSteeringBinding? = null
    private val binding get() = _binding!!
    private val homepageViewModel: HomePageViewModel by activityViewModels()
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

    /**********************************************************************************************
     * Display and data loading
     **********************************************************************************************/
    private fun loadData() {
        arguments?.let {
            binding.viewModel = deviceSteeringViewModel
            val device = it.getParcelable<Device>(DEVICE_ARG)
            when (device!!.productType) {

                ProductType.Light-> {
                    val data = device.toLight()
                    binding.light = data
                    deviceSteeringViewModel.setLight(data)
                    setLightObservers()
                    lightSeekbarListener()
                    binding.lightInfoViewer.viewLight.visibility = View.VISIBLE
                    binding.rollerShutterInfoViewer.viewRs.visibility = View.GONE
                    binding.heaterInfoViewer.viewHeater.visibility = View.GONE
                }

                ProductType.RollerShutter-> {
                    val data = device.toRollerShutter()
                    binding.rollerShutter = data
                    deviceSteeringViewModel.setRollerShutter(data)
                    setRsObservers()
                    rstSeekbarListener()
                    binding.lightInfoViewer.viewLight.visibility = View.GONE
                    binding.rollerShutterInfoViewer.viewRs.visibility = View.VISIBLE
                    binding.heaterInfoViewer.viewHeater.visibility = View.GONE
                }

                else -> {
                    val data = device.toHeater()
                    binding.heater = data
                    deviceSteeringViewModel.setHeater(data)
                    setHeaterObservers()
                    heaterSeekbarListener()
                    binding.lightInfoViewer.viewLight.visibility = View.GONE
                    binding.rollerShutterInfoViewer.viewRs.visibility = View.GONE
                    binding.heaterInfoViewer.viewHeater.visibility = View.VISIBLE
                }
            }
        }
    }

    /**********************************************************************************************
     * Observers
     **********************************************************************************************/

    // Observes changes in DeviceSteeringViewModel's lightLivedata
    private fun setLightObservers() {
        deviceSteeringViewModel.lightLivedata.observe(viewLifecycleOwner) {
            it?.let {
                binding.light = it
                homepageViewModel.updateDevice(it.toDevice())
            }
        }
    }

    // Observes changes in DeviceSteeringViewModel's rsLiveData
    private fun setRsObservers() {
        deviceSteeringViewModel.rsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.rollerShutter = it
                homepageViewModel.updateDevice(it.toDevice())
            }
        }
    }

    // Observes changes in DeviceSteeringViewModel's heaterLiveData
    private fun setHeaterObservers() {
        deviceSteeringViewModel.heaterLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.heater = it
                homepageViewModel.updateDevice(it.toDevice())
            }
        }
    }


    /**********************************************************************************************
     * Listeners
     **********************************************************************************************/
    private fun lightSeekbarListener() {
        binding.lightInfoViewer.progressBarLight.setOnClickListener {
            deviceSteeringViewModel.seekBarLightListener(binding.lightInfoViewer.progressBarLight.progress)
        }
    }

    private fun rstSeekbarListener() {
        binding.rollerShutterInfoViewer.progressBarRs.setOnClickListener {
            deviceSteeringViewModel.seekBarRSListener(binding.rollerShutterInfoViewer.progressBarRs.progress)
        }
    }

    private fun heaterSeekbarListener() {
        binding.heaterInfoViewer.progressBarHeater.setOnClickListener {
            deviceSteeringViewModel.seekBarHeaterListener(binding.heaterInfoViewer.progressBarHeater.progress)
        }
    }

    private fun setUpButtonListener() {
        binding.up.setOnClickListener {
            findNavController().navigate(DeviceSteeringFragmentDirections.actionDeviceSteeringFragmentToHomePageFragment())
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