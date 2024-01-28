package com.aghajari.videosharing.nav

import androidx.compose.runtime.Composable
import com.aghajari.videosharing.screen.home.ui.HomeScreen
import com.aghajari.videosharing.screen.login.ui.LoginScreen
import com.aghajari.videosharing.screen.login.ui.LoginValidationScreen
import com.aghajari.videosharing.screen.login.ui.LoginUsername

enum class Route(
    val content: @Composable () -> Unit
) {
    Login({ LoginScreen() }),
    LoginValidation({ LoginValidationScreen() }),
    LoginUsername({ LoginUsername() }),
    Home({ HomeScreen() }),
}