package com.toastmeister1.kestrel.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.toastmeister1.kestrel.core.navigation.AppComposeNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    @Inject
    internal lateinit var appComposeNavigator: AppComposeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(navigator = appComposeNavigator)
        }
    }
}
