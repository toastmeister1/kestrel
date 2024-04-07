package com.toastmeister1.kestrel.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme
import com.toastmeister1.kestrel.core.navigation.AppComposeNavigator
import com.toastmeister1.kestrel.core.navigation.KestrelScreens
import com.toastmeister1.kestrel.feature.animation.scale.ScaleAnimationTest
import com.toastmeister1.kestrel.feature.home.HomeScreen

@Composable
fun MainScreen(navigator: AppComposeNavigator) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        navigator.handleNavigationCommands(navController)
    }

    KestrelTheme {
        NavHost(
            navController = navController,
            startDestination = KestrelScreens.Home.route
        ) {
            composable(route = KestrelScreens.Home.route) { HomeScreen(navigator) }

            composable(route = KestrelScreens.Animation.route) { ScaleAnimationTest() }
        }
    }
}