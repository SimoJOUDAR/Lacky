package fr.mjoudar.lackey.presentation.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fr.mjoudar.lackey.R
import fr.mjoudar.lackey.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

        setButtons() // TODO: Test

        return binding.root
    }

    private fun setButtons() {
        binding.button1.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_deviceSteeringFragment)
        }
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_myAccountFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}