package com.toastmeister1.kestrel

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
internal class KestrelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}