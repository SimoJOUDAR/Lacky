package fr.mjoudar.lackey.presentation.gridView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import fr.mjoudar.lackey.databinding.FragmentGridViewBinding
import fr.mjoudar.lackey.domain.models.*
import fr.mjoudar.lackey.presentation.adapters.GridViewAdapter
import fr.mjoudar.lackey.presentation.homePage.HomePageFragment
import fr.mjoudar.lackey.presentation.homePage.HomePageFragmentDirections

/**********************************************************************************************
 * GridViewFragment class - the Fragment responsible of displaying devices on a grid
 **********************************************************************************************/
class GridViewFragment : Fragment() {

    private var _binding: FragmentGridViewBinding? = null
    private val binding get() = _binding!!
    private var option: Int = 0
    private lateinit var adapter: GridViewAdapter
    private val viewModel: GridViewViewModel by activityViewModels()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /***********************************************************************************************
     ** UI
     ***********************************************************************************************/

    // Set the RecyclerView's Adapter and LayoutManager
    private fun setRecyclerView() {
        val onItemClickListener = View.OnClickListener { itemView ->
            val device = when (val item = itemView.tag) {
                is Light -> item.toDevice()
                is RollerShutter -> item.toDevice()
                is Heater -> item.toDevice()
                else -> null
            }
            device?.let {
                findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToDeviceSteeringFragment(it))
            }
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

    // Return the number of columns that can fit in a Grid depending on the screen dimensions
    private fun columnNumberCalculator() : Int {
        val recyclerViewItemWidth = 176
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / recyclerViewItemWidth).toInt()
    }

    /***********************************************************************************************
     ** Data retrieval
     ***********************************************************************************************/

    // Remotely order the ViewModel to start fetching Devices data
    private fun fetchData() {
        viewModel.fetchDevices()
    }

    // Observe changes in HomePageViewModel's devicesLiveData to update the view's data
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

    // Filter a Device List based on Device productType and options contained in the Bundle
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

    companion object {
        const val OPTION = "option"
    }

}