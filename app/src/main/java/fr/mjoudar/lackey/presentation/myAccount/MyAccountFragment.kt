package fr.mjoudar.lackey.presentation.myAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import fr.mjoudar.lackey.R

class MyAccountFragment : Fragment() {

    private val viewModel: MyAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }



    private fun fetchUser() {
        viewModel.fetchUser()   //TODO: update only if DataStorage is uninitialized - otherwise, we keep what's saved
    }

    private fun observeUser() {
        // TODO: To dismiss LiveDate after first initialization using _observeOnce_ or using _removeObserver(this)_
    }

}