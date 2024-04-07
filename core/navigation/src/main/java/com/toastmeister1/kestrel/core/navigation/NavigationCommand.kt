package com.toastmeister1.kestrel.core.navigation

import androidx.navigation.NavOptions

sealed class NavigationCommand {
    data object NavigateUp : NavigationCommand()

    data class NavigateToRoute(val route: String, val options: NavOptions? = null) : NavigationCommand()

    data class NavigateUpWithResult<T>(val key: String, val result: T, val route: String? = null) : NavigationCommand()

    data class PopUpToRoute(val route: String, val inclusive: Boolean) : NavigationCommand()
}
