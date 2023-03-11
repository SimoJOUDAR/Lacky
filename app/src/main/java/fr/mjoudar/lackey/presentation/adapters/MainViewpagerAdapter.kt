package fr.mjoudar.lackey.presentation.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.mjoudar.lackey.presentation.gridView.GridViewFragment

class MainViewpagerAdapter(
    list: ArrayList<GridViewFragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle)  {

    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("option", position)
        fragmentList[position].arguments = bundle
        return fragmentList[position]
    }
}