package fr.mjoudar.lackey.presentation.myAccount

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.mjoudar.lackey.R
import fr.mjoudar.lackey.databinding.FragmentHomePageBinding
import fr.mjoudar.lackey.databinding.FragmentMyAccountBinding
import java.util.*

@AndroidEntryPoint
class MyAccountFragment : Fragment() {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyAccountViewModel by viewModels()

    // Date picker listeners
    private val dateListener by lazy {
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            var user = binding.infoEditor.user
            user?.birthDate = (calendar.time).time
            binding.infoEditor.user = user
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUser()
        observeUser()
        setEditButtonListener()
        setSaveButtonListener()
        setDatePickerListener()
        setUpButtonListener()
    }

    private fun fetchUser() {
        viewModel.retrieveUser()
    }

    private fun observeUser() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.user = it
        }
    }

    private fun setEditButtonListener() {
        binding.infoViewer.buttonEdit.setOnClickListener {
            binding.infoViewer.infoView.visibility = View.GONE
            binding.infoEditor.infoEdit.visibility = View.VISIBLE
        }
    }

    private fun setSaveButtonListener() {
        binding.infoEditor.buttonSave.setOnClickListener {
            binding.infoEditor.infoEdit.visibility = View.GONE
            binding.infoViewer.infoView.visibility = View.VISIBLE
            binding.infoEditor.user?.address?.zipCode = binding.infoEditor.zip.text.toString().toInt()
            viewModel.updateUser(binding.infoEditor.user!!)
        }
    }

    private fun setDatePickerListener() {
        binding.infoEditor.birthDateAutocomplete.setOnClickListener {
            var calendar = Calendar.getInstance()
            val time = binding.infoEditor.user?.birthDate ?: 0
            if (time > 0) calendar.timeInMillis = time
            datePickerLauncher(dateListener, calendar)
        }
    }

    private fun datePickerLauncher(listener:  DatePickerDialog.OnDateSetListener, calendar: Calendar) {
        val picker = DatePickerDialog(
            requireContext(),
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }

    private fun setUpButtonListener() {
        binding.up.setOnClickListener {
            findNavController().navigate(MyAccountFragmentDirections.actionMyAccountFragmentToHomePageFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}