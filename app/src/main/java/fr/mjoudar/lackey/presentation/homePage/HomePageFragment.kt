package fr.mjoudar.lackey.presentation.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import fr.mjoudar.lackey.R
import fr.mjoudar.lackey.databinding.FragmentHomePageBinding
import fr.mjoudar.lackey.presentation.adapters.MainViewpagerAdapter
import fr.mjoudar.lackey.presentation.gridView.GridViewFragment

/***********************************************************************************************
 * HomePageFragment class - the Fragment responsible of displaying the ViewPager
 ***********************************************************************************************/
class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MainViewpagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewpager()
        setOnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /***********************************************************************************************
     ** ViewPager
     ***********************************************************************************************/

    // Set the ViewPager Adapter and TabLayout
    private fun setViewpager() {
        findNavController().previousBackStackEntry?.savedStateHandle?.set("key", true)
        // The fragment list contains four instances of GridViewFragment. Each one acts as a reusable filter tab : All, Light, Shutter, Heater.
        val fragmentList = arrayListOf(GridViewFragment(), GridViewFragment(), GridViewFragment(), GridViewFragment())
        adapter = MainViewpagerAdapter(fragmentList, childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                ALL_TAB -> {
                    tab.text = resources.getString(R.string.all)
                }
                LIGHT_TAB -> {
                    tab.text = resources.getString(R.string.light)
                }
                ROLLER_SHUTTER_TAB -> {
                    tab.text = resources.getString(R.string.shutter)
                }
                HEATER_TAB -> {
                    tab.text = resources.getString(R.string.heater)
                }
            }
        }.attach()
    }

    /***********************************************************************************************
     ** Listeners
     ***********************************************************************************************/

    // Listen to profile button clicks to navigate to MyAccountFragment
    private fun setOnClickListener() {
        binding.profile.setOnClickListener {
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToMyAccountFragment())
        }
    }

    companion object {
        const val ALL_TAB = 0
        const val LIGHT_TAB = 1
        const val ROLLER_SHUTTER_TAB = 2
        const val HEATER_TAB = 3
    }
}