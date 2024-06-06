package com.example.europrofile.core.ui

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.europrofile.R

fun Fragment.findTopNavController() : NavController {
    val topLevelController = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
    return topLevelController?.navController ?: findNavController()
}