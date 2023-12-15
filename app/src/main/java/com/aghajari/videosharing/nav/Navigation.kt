package com.aghajari.videosharing.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    NavHost(
        enterTransition = {
            fadeIn(tween(500)) +
                    slideIntoContainer(
                        getDirection(),
                        animationSpec = tween(500)
                    )
        },
        exitTransition = {
            fadeOut(tween(500)) +
                    slideOutOfContainer(
                        getDirection(),
                        animationSpec = tween(500)
                    )
        },
        modifier = modifier,
        navController = LocalAppState.current.navController,
        startDestination = Route.Login.name
    ) {
        Route.values().forEach { route ->
            composable(
                route = route.name
            ) {
                route.content.invoke()
            }
        }
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.getDirection()
        : AnimatedContentTransitionScope.SlideDirection {
    val target = Route.values().indexOfFirst { it.name == targetState.destination.route }
    val initial = Route.values().indexOfFirst { it.name == initialState.destination.route }
    return if (target >= initial) {
        AnimatedContentTransitionScope.SlideDirection.Start
    } else {
        AnimatedContentTransitionScope.SlideDirection.End
    }
}