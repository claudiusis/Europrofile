package com.example.europrofile.ui.accountpages.contactinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.europrofile.R
import com.example.europrofile.databinding.FragmentContactInformationBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class ContactInformationFragment : Fragment() {

    private lateinit var binding : FragmentContactInformationBinding

    private lateinit var mapView : MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactInformationBinding.inflate(layoutInflater)

        mapView = binding.contactMap

        mapView.mapWindow.map.move(
            CameraPosition(
                Point(54.337950, 48.439942),
                /* zoom = */ 10.0f,
                /* azimuth = */ 0.0f,
                /* tilt = */ 30.0f
            )
        )

        val imageProvider = ImageProvider.fromResource(this.requireContext(), R.drawable.map_icon)
        val placeMarkArray = listOf(
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(54.269856, 48.307483)
            setIcon(imageProvider)
                                                                  },
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = Point(54.372419, 48.576986)
                setIcon(imageProvider)
            },
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = Point(54.379540, 48.576258)
                setIcon(imageProvider)
            }
            )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}