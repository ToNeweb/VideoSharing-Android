package com.aghajari.videosharing.ui.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aghajari.videosharing.nav.LocalAppState
import com.aghajari.videosharing.nav.Route
import com.aghajari.videosharing.screen.login.viewmodel.LoginViewModel
import com.aghajari.videosharing.utils.sharedViewModel
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Image(
    imageModel: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
    contentDescription: String? = null,
    @DrawableRes placeholder: Int = 0,
    @DrawableRes failureImageRes: Int = placeholder,
    requestBuilder: (RequestBuilder<Drawable>) -> Unit = {}
) {
    GlideImage(
        modifier = modifier,
        model = imageModel,
        contentDescription = contentDescription,
        contentScale = contentScale,
        alignment = alignment,
        colorFilter = colorFilter
    ) { builder ->
        builder
            .placeholder(placeholder)
            .error(failureImageRes)
            .apply(requestBuilder)
    }
}

@Composable
fun ColumnScope.Space(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun RowScope.Space(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun ColumnScope.Space(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}

@Composable
fun RowScope.Space(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    label: String,
    keyboardType: KeyboardType,
    action: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 34.dp),
        value = text,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Start
        ),
        onValueChange = { text = it },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { action.invoke(text) }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}