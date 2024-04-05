package com.example.europrofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.europrofile.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHost.navController.navInflater
        val graph = inflater.inflate(R.navigation.test_navig)


        val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
        if (sharedPreferences.getString("UID", "-1").equals("-1")){
            graph.setStartDestination(R.id.loginFragment)
        } else {
            graph.setStartDestination(R.id.tabsFragment)
        }

        navHost.navController.graph = graph
    }
}