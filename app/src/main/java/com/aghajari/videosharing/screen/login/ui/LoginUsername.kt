package com.aghajari.videosharing.screen.login.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aghajari.videosharing.nav.LocalAppState
import com.aghajari.videosharing.nav.Route
import com.aghajari.videosharing.screen.login.viewmodel.LoginViewModel
import com.aghajari.videosharing.ui.component.OutlinedTextField
import com.aghajari.videosharing.ui.component.Space
import com.aghajari.videosharing.ui.preview.VideoSharingPreview
import com.aghajari.videosharing.ui.text.bold
import com.aghajari.videosharing.utils.KeyboardEffect
import com.aghajari.videosharing.utils.hideKeyboard
import com.aghajari.videosharing.utils.sharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginUsername() {
    val keyboardHeight = remember {
        Animatable(0f)
    }
    KeyboardEffect(keyboardHeight)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = keyboardHeight.value.dp)
    ) {
        Text(
            text = "Welcome!",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        Space(size = 4.dp)
        Text(
            text = buildAnnotatedString {
                append("Hi, Welcome to ")
                bold("VideoSharing")
                append("!\n")
                append("Please choose a username for your account.")
            },
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Space(size = 24.dp)
        UsernameField()
        Space(size = 24.dp)
        Text(
            text = buildAnnotatedString {
                append("You can use ")
                bold("a-z")
                append(", ")
                bold("0-9")
                append("and underscores.\n")
                append("Minimum length is ")
                bold("4")
                append(" characters.")
            },
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun UsernameField(
    loginViewModel: LoginViewModel = sharedViewModel()
) {
    val view = LocalView.current
    val appState = LocalAppState.current
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        label = "Username",
        keyboardType = KeyboardType.Text
    ) { text ->
        scope.launch {
            if (loginViewModel.updateEmailAddress(text)) {
                hideKeyboard(view)
                delay(200)
                appState.navigateTo(Route.LoginValidation)
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginUsername() {
    VideoSharingPreview(Route.LoginUsername)
}