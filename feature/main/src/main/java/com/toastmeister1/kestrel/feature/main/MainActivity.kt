package com.toastmeister1.kestrel.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.toastmeister1.kestrel.core.data.repository.MovieRepository
import com.toastmeister1.kestrel.core.navigation.AppComposeNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    @Inject
    internal lateinit var appComposeNavigator: AppComposeNavigator

    @Inject
    internal lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                val result = movieRepository.fetchPopular(1)

                println(result)
            }
            MainScreen(navigator = appComposeNavigator)
        }
    }
}
