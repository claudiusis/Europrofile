package com.example.europrofile.ui.tabs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavHost
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.europrofile.R
import com.example.europrofile.databinding.FragmentTabsBinding

class TabsFragment : Fragment() {

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentTabsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.FAB.setColorFilter(Color.rgb(255,255,255))
        binding.bottomNavigation.menu.getItem(2).isEnabled = false

        val navHost = childFragmentManager.findFragmentById(R.id.fragment_tabs_container) as NavHost
        val navController = navHost.navController

/*        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            Log.d("QWERTY", menuItem.itemId.toString())
            false
        }*/

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        binding.FAB.setOnClickListener {
            findNavController().navigate(R.id.action_tabsFragment_to_makeOrderFragment2)
        }
    }

}