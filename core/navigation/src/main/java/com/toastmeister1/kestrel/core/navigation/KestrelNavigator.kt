package com.toastmeister1.kestrel.core.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import javax.inject.Inject

class KestrelNavigator @Inject constructor() : AppComposeNavigator() {

    override fun navigate(route: String, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
        val options = optionsBuilder?.let { navOptions(it) }
        navigationCommands.tryEmit(NavigationCommand.NavigateToRoute(route, options))
    }

    override fun navigateAndClearBackStack(route: String) {
        navigationCommands.tryEmit(
            NavigationCommand.NavigateToRoute(
                route,
                navOptions {
                    popUpTo(0)
                },
            ),
        )
    }

    override fun popUpTo(route: String, inclusive: Boolean) {
        navigationCommands.tryEmit(NavigationCommand.PopUpToRoute(route, inclusive))
    }

    override fun <T> navigateBackWithResult(
        key: String,
        result: T,
        route: String?,
    ) {
        navigationCommands.tryEmit(
            NavigationCommand.NavigateUpWithResult(
                key = key,
                result = result,
                route = route,
            ),
        )
    }
}
