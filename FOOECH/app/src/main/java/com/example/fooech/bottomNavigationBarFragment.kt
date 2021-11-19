package com.example.fooech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_bottom_navigation_bar.*

class bottomNavigationBarFragment : Fragment(
    R.layout.fragment_bottom_navigation_bar
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context =activity
        //for the bottom navigation bar
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false;
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_icon -> Toast.makeText(
                    context, "home clicked", Toast.LENGTH_SHORT)
                    .show()
                R.id.search_icon -> Toast.makeText(
                    context, "search clicked", Toast.LENGTH_SHORT)
                    .show()
                R.id.explore_icon -> Toast.makeText(
                    context, "explore clicked", Toast.LENGTH_SHORT)
                    .show()
                R.id.profile_icon -> Toast.makeText(
                    context, "profile clicked", Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }
        floatingAdd.setOnClickListener {
          Toast.makeText(
            context, "add clicked", Toast.LENGTH_SHORT)
            .show()

        }
    }

}