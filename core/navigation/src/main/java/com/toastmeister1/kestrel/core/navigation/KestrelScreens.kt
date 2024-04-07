package com.toastmeister1.kestrel.core.navigation

import androidx.navigation.NamedNavArgument

sealed class KestrelScreens(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : KestrelScreens(route = "home", navArguments = emptyList())

    data object Animation : KestrelScreens(route = "animation", navArguments = emptyList())
}