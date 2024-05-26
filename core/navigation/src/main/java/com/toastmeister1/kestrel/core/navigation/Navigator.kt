package com.toastmeister1.kestrel.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class Navigator {
    val navigationCommands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = Int.MAX_VALUE)

    fun navigateUp(route: String) {
        navigationCommands.tryEmit(NavigationCommand.NavigateToRoute(route))
    }
}

abstract class AppComposeNavigator : Navigator() {
    abstract fun navigate(route: String, optionsBuilder: (NavOptionsBuilder.() -> Unit)? = null)
    abstract fun <T> navigateBackWithResult(key: String, result: T, route: String?)

    abstract fun popUpTo(route: String, inclusive: Boolean)
    abstract fun navigateAndClearBackStack(route: String)

    suspend fun handleNavigationCommands(navController: NavController) {
        navigationCommands.collect { navController.handleComposeNavigationCommand(it) }
    }

    private fun NavController.handleComposeNavigationCommand(navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            is NavigationCommand.NavigateToRoute -> {
                navigate(navigationCommand.route, navigationCommand.options)
            }

            is NavigationCommand.NavigateUp -> navigateUp()

            is NavigationCommand.PopUpToRoute -> popBackStack(
                navigationCommand.route,
                navigationCommand.inclusive,
            )

            is NavigationCommand.NavigateUpWithResult<*> -> {
                navUpWithResult(navigationCommand)
            }
        }
    }

    private fun NavController.navUpWithResult(
        navigationCommand: NavigationCommand.NavigateUpWithResult<*>,
    ) {
        val backStackEntry = navigationCommand.route?.let { getBackStackEntry(it) } ?: previousBackStackEntry
        backStackEntry?.savedStateHandle?.set(
            navigationCommand.key,
            navigationCommand.result,
        )

        navigationCommand.route?.let {
            popBackStack(it, false)
        } ?: run {
            navigateUp()
        }
    }
}
