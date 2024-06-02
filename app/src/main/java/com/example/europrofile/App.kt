package com.example.europrofile

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("980e699b-fa26-4fdd-b978-50b42ff90713")
    }
}