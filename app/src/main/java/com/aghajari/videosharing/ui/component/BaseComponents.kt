package com.aghajari.videosharing.ui.component

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    label: String,
    keyboardType: KeyboardType,
    text: MutableState<String> = remember { mutableStateOf("") },
    action: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 34.dp),
        value = text.value,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Start
        ),
        onValueChange = { text.value = it },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { action.invoke(text.value) }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}