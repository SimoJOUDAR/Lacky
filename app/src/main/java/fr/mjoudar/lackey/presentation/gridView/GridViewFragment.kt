package fr.mjoudar.lackey.presentation.gridView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import fr.mjoudar.lackey.R
import fr.mjoudar.lackey.databinding.FragmentGridViewBinding
import fr.mjoudar.lackey.domain.models.*
import fr.mjoudar.lackey.presentation.adapters.GridViewAdapter
import fr.mjoudar.lackey.presentation.deviceSteering.DeviceSteeringFragment
import fr.mjoudar.lackey.presentation.homePage.HomePageFragment
import fr.mjoudar.lackey.presentation.homePage.HomePageViewModel
import kotlin.math.log

class GridViewFragment : Fragment() {

    private var _binding: FragmentGridViewBinding? = null
    private val binding get() = _binding!!
    private var option: Int = 0
    private lateinit var adapter: GridViewAdapter
    private val viewModel: HomePageViewModel by activityViewModels()
    private var devices = listOf<Device>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGridViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        fetchData()
        observeDevices()
    }

    /***********************************************************************************************
     ** UI
     ***********************************************************************************************/
    private fun setRecyclerView() {
        val onItemClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag
            val bundle = Bundle()
            when (item) {
                is Light -> bundle.putParcelable(DeviceSteeringFragment.DEVICE_ARG, item.toDevice())
                is RollerShutter -> bundle.putParcelable(DeviceSteeringFragment.DEVICE_ARG, item.toDevice())
                is Heater -> bundle.putParcelable(DeviceSteeringFragment.DEVICE_ARG, item.toDevice())
            }
            itemView.findNavController().navigate(R.id.deviceSteeringFragment, bundle)
        }

        val onDeleteClickListener = View.OnClickListener { itemView ->

            when (val item = itemView.tag) {
                is Light -> viewModel.deleteDevice(item.id)
                is RollerShutter -> viewModel.deleteDevice(item.id)
                is Heater -> viewModel.deleteDevice(item.id)
            }
        }

        val onContextClickListener = View.OnContextClickListener { true }
        adapter = GridViewAdapter(onItemClickListener, onDeleteClickListener, onContextClickListener, viewModel)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), columnNumberCalculator()) // For screen size adaptability
    }

    private fun columnNumberCalculator() : Int {
        val recyclerViewItemWidth = 176
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / recyclerViewItemWidth).toInt()
    }

    /***********************************************************************************************
     ** Data retrieval
     ***********************************************************************************************/
    private fun fetchData() {
        viewModel.fetchDevices()
    }

    private fun observeDevices() {
        if (devices.isEmpty()) binding.progressBar.visibility = View.VISIBLE
        viewModel.devicesLiveData.observe(viewLifecycleOwner) {
            arguments?.let { arg -> filterBy(arg, it)}
            if (devices.isNotEmpty()) binding.progressBar.visibility = View.GONE
        }
    }

    /***********************************************************************************************
     ** Data filtering
     ***********************************************************************************************/
    private fun filterBy(bundle: Bundle, data: List<Device>) {
        option = bundle.getInt(OPTION, 0)
        when (option) {
            HomePageFragment.ALL_TAB -> {
                devices = data
                adapter.setData(devices)
                binding.recyclerview.adapter = adapter
                return
            }
            HomePageFragment.LIGHT_TAB -> {
                devices = data.filter { it.productType == ProductType.Light }
                adapter.setData(devices)
                binding.recyclerview.adapter = adapter
                return
            }
            HomePageFragment.ROLLER_SHUTTER_TAB -> {
                devices = data.filter { it.productType == ProductType.RollerShutter }
                adapter.setData(devices)
                binding.recyclerview.adapter = adapter
                return
            }
            HomePageFragment.HEATER_TAB -> {
                devices = data.filter { it.productType == ProductType.Heater }
                adapter.setData(devices)
                binding.recyclerview.adapter = adapter
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val OPTION = "option"
    }

}