package fr.mjoudar.lackey.presentation.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import fr.mjoudar.lackey.databinding.FragmentHomePageBinding
import fr.mjoudar.lackey.presentation.adapters.MainViewpagerAdapter
import fr.mjoudar.lackey.presentation.gridView.GridViewFragment

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
    }

    private fun setViewpager() {
        findNavController().previousBackStackEntry?.savedStateHandle?.set("key", true)
        // The fragment list contains four instances of GridViewFragment. Each one acts as a reusable filter tab : All, Light, Shutter, Heater.
        val fragmentList = arrayListOf(GridViewFragment(), GridViewFragment(), GridViewFragment(), GridViewFragment())
        adapter = MainViewpagerAdapter(fragmentList, childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        //binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "All"
                }
                1 -> {
                    tab.text = "Light"
                }
                2 -> {
                    tab.text = "Shutter"
                }
                3 -> {
                    tab.text = "Heater"
                }
            }
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}