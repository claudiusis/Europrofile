package com.example.europrofile

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.ActivityMainBinding
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.accountpages.reviewcreation.ReviewViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModelProfile: ProfileViewModel by viewModels()
    private val reviewViewModel: ReviewViewModel by viewModels()
    lateinit var fusedLocationServices: FusedLocationProviderClient

    lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationServices = LocationServices.getFusedLocationProviderClient(this)

        requestPermissions()

        binding = ActivityMainBinding.inflate(layoutInflater)

        window.statusBarColor = Color.BLACK

        MapKitFactory.initialize(this)

        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHost.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_navigation_graph)


        val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
        if (sharedPreferences.getString("UID", "-1").equals("-1")) {
            graph.setStartDestination(R.id.loginFragment)
        } else {
            graph.setStartDestination(R.id.tabsFragment)
            uid = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("UID", "-1")
                ?: "-1"
            viewModelProfile.getUserInfo(uid)
        }

        navHost.navController.graph = graph

        reviewViewModel.reviewSetState.observe(this) {
            when (it) {
                is RequestResult.Success -> {
                    (viewModelProfile.userInfo.value as RequestResult.Success).data.countOfReviews++
                    viewModelProfile.changeInfo(
                        (viewModelProfile.userInfo.value as RequestResult.Success).data,
                        FireBaseTags.COUNT_OF_REVIEWS_CHANGES
                    )
                }

                else -> {}
            }
        }

    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 10
        )

    }

}
/*        viewModelProfile.userInfo.observe(this){
            uid = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("UID", "-1")?:"-1"
            viewModelProfile.getUserInfo(uid)
        }*
    }

    override fun onResume() {
        super.onResume()
     //   requestPermissions()
    }

/*    fun requestPermissions(){
        ActivityCompat.requestPermissions(this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE)
    }*/


/*    fun checkPermissions() : Boolean {
        return !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
    }

    fun isLocationEnabled() : Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        return  locationManager.isLocationEnabled

    }*/
}*/
