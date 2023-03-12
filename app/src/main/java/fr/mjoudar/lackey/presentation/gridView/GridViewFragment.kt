package fr.mjoudar.lackey.presentation.gridView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import fr.mjoudar.lackey.R
import fr.mjoudar.lackey.databinding.FragmentGridViewBinding
import fr.mjoudar.lackey.databinding.FragmentHomePageBinding

class GridViewFragment : Fragment() {

    private var _binding: FragmentGridViewBinding? = null
    private val binding get() = _binding!!
    private var option: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGridViewBinding.inflate(inflater, container, false)
        arguments?.let { it -> filterBy(it)}
        return binding.root
    }

    private fun filterBy(bundle: Bundle) {
        option = bundle.getInt("option", 0)
//        when (option) {
//            0 -> binding.textView.text = "GridViewFragment - All"
//            1 -> binding.textView.text = "GridViewFragment - Light"
//            2 -> binding.textView.text = "GridViewFragment - Shutter"
//            3 -> binding.textView.text = "GridViewFragment - Heater"
//        }
    }

}