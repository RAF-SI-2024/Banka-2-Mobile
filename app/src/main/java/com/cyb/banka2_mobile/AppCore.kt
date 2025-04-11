package com.cyb.banka2_mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppCore: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}