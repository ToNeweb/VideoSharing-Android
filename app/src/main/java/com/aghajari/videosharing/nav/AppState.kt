package com.aghajari.videosharing.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

val LocalAppState = staticCompositionLocalOf<AppState> {
    error("no state provided")
}

class AppState(
    val navController: NavHostController
) {

    fun navigateTo(
        route: Route,
        pop: Boolean = false
    ) {
        if (pop) {
            navController.popBackStack()
        }
        navController.navigate(route.name)
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember {
    AppState(navController)
}