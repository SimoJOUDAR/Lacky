package fr.mjoudar.lackey.presentation.gridView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import fr.mjoudar.lackey.databinding.FragmentGridViewBinding
import fr.mjoudar.lackey.domain.models.*
import fr.mjoudar.lackey.presentation.adapters.GridViewAdapter
import fr.mjoudar.lackey.presentation.homePage.HomePageFragment
import fr.mjoudar.lackey.presentation.homePage.HomePageFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        collectDevices()
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

    private fun displayIsLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.INVISIBLE
        binding.errorLayout.errorPage.visibility = View.GONE
    }

    private fun displayError(e : Exception) {
        binding.errorLayout.errorText.text = e.toString()
        binding.progressBar.visibility = View.GONE
        binding.recyclerview.visibility = View.GONE
        binding.errorLayout.errorPage.visibility = View.VISIBLE
    }

    private fun displayData(data: List<Device>) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
        binding.errorLayout.errorPage.visibility = View.GONE
        arguments?.let { arg -> filterBy(arg, data)}
    }

    /***********************************************************************************************
     ** Subscribers
     ***********************************************************************************************/

    // Collect changes in HomePageViewModel's devicesUiState to update the view's data
    private fun collectDevices() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.devicesUiState.collectLatest {
                    when (it) {
                        is DeviceUiState.Loading -> displayIsLoading()
                        is DeviceUiState.Error -> displayError(it.error)
                        is DeviceUiState.Success -> displayData(it.devices)
                    }
                }
            }
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

    /***********************************************************************************************
     ** Utils
     ***********************************************************************************************/
    // Return the number of columns that can fit in a Grid depending on the screen dimensions
    private fun columnNumberCalculator() : Int {
        val recyclerViewItemWidth = 176
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / recyclerViewItemWidth).toInt()
    }

    companion object {
        const val OPTION = "option"
    }
}