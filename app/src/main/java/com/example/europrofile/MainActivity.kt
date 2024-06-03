package com.example.europrofile

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.ActivityMainBinding
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.accountpages.reviewcreation.ReviewViewModel
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModelProfile: ProfileViewModel by viewModels()
    private val reviewViewModel: ReviewViewModel by viewModels()

    lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)

        window.statusBarColor = Color.BLACK

        MapKitFactory.initialize(this)

        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHost.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_navigation_graph)


        val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
        if (sharedPreferences.getString("UID", "-1").equals("-1")){
            graph.setStartDestination(R.id.loginFragment)
        } else {
            graph.setStartDestination(R.id.tabsFragment)
            uid = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("UID", "-1")?:"-1"
            viewModelProfile.getUserInfo(uid)
        }

        navHost.navController.graph = graph

        reviewViewModel.reviewSetState.observe(this){
            when (it){
                is RequestResult.Success -> {
                    (viewModelProfile.userInfo.value as RequestResult.Success).data.countOfReviews++
                    viewModelProfile.changeInfo((viewModelProfile.userInfo.value as RequestResult.Success).data,
                        FireBaseTags.COUNT_OF_REVIEWS_CHANGES)
                } else -> {}
            }
        }

/*        viewModelProfile.userInfo.observe(this){
            uid = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("UID", "-1")?:"-1"
            viewModelProfile.getUserInfo(uid)
        }*/
    }
}
