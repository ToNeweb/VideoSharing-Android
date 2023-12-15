package com.aghajari.videosharing.screen.login.ui

import android.animation.ArgbEvaluator
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.Animatable as FloatAnimatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aghajari.videosharing.R
import com.aghajari.videosharing.nav.AppState
import com.aghajari.videosharing.nav.LocalAppState
import com.aghajari.videosharing.nav.Route
import com.aghajari.videosharing.screen.login.model.ValidationStatus
import com.aghajari.videosharing.screen.login.viewmodel.LoginViewModel
import com.aghajari.videosharing.ui.animation.OvershootEasing
import com.aghajari.videosharing.ui.component.Image
import com.aghajari.videosharing.ui.component.Space
import com.aghajari.videosharing.ui.preview.VideoSharingPreview
import com.aghajari.videosharing.ui.text.bold
import com.aghajari.videosharing.utils.sharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginValidationScreen(
    loginViewModel: LoginViewModel = sharedViewModel()
) {
    val selectedColor = MaterialTheme.colorScheme.primary
    val defaultColor = MaterialTheme.colorScheme.onSecondary
    val scope = rememberCoroutineScope()
    val appState = LocalAppState.current
    val code = remember {
        CodeAnimationHelper(
            scope,
            appState,
            selectedColor.toArgb(),
            defaultColor.toArgb(),
            Color.Red.toArgb(),
            Color.Green.toArgb()
        )
    }
    val status = loginViewModel.validationStatus.collectAsState()
    code.updateValidationStatus(status.value)
    loginViewModel.clearValidation()

    if (code.text.value.length == 5) {
        loginViewModel.validateCode(code.text.value)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 56.dp)
    ) {
        Space(size = 124.dp)
        Text(
            text = "Check your email address",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        Space(size = 18.dp)
        Text(
            text = buildAnnotatedString {
                append("We've sent the code to your email:\n")
                bold(loginViewModel.emailAddress.orEmpty())
            },
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Space(size = 32.dp)
        CodeRow(code)
        Space(weight = 1f)
        DigitKeyboard(code)
    }
}

@Composable
private fun CodeRow(
    code: CodeAnimationHelper
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(5) { index ->
            val indexedCode = if (code.text.value.length > index) {
                code.text.value.substring(index, index + 1)
            } else if ((code.isStatusUpdating ||
                        index == code.fractionIndex) &&
                code.scaledText.length > index
            ) {
                code.scaledText.substring(index, index + 1)
            } else {
                ""
            }

            val (scale, offset) = if (code.isStatusUpdating || index == code.fractionIndex) {
                val scale = if (code.fraction.targetValue == -1f) {
                    1f - (code.fraction.value * -1f)
                } else {
                    1f
                }
                val offset = if (code.fraction.targetValue == 1f) {
                    40.dp * (1f - code.fraction.value)
                } else {
                    0.dp
                }

                Pair(scale, offset)
            } else {
                Pair(1f, 0.dp)
            }

            val shape = RoundedCornerShape(4.dp)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .sizeIn(
                        minHeight = 42.dp,
                        minWidth = 34.dp
                    )
                    .border(
                        width = 2.dp,
                        color = code.colorOf(index),
                        shape = shape
                    )
                    .clip(shape)
            ) {
                Text(
                    text = indexedCode,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .offset(y = 2.dp + offset)
                        .scale(scale)
                )
            }
        }
    }
}

@Composable
private fun DigitKeyboard(
    code: CodeAnimationHelper
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        repeat(3) { columnIndex ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(3) { rowIndex ->
                    KeyboardButton(
                        button = columnIndex * 3 + rowIndex + 1,
                        code = code
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Space(weight = 1f)
            KeyboardButton(
                button = 0,
                code = code
            )
            KeyboardButton(
                button = -1,
                code = code,
            )
        }
    }
}

@Composable
private fun RowScope.KeyboardButton(
    button: Int,
    code: CodeAnimationHelper
) {
    val shape = RoundedCornerShape(8.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 46.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = shape
            )
            .clip(shape)
            .clickable {
                if (button >= 0 && code.text.value.length < 5) {
                    code.enter(button)
                } else if (button == -1) {
                    code.backspace()
                }
            }
    ) {
        if (button >= 0) {
            Text(
                text = button.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = with(LocalDensity.current) { 24.dp.toSp() },
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = 2.dp)
            )
        } else {
            Image(
                imageModel = R.drawable.backspace,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
        }
    }
}

private data class CodeAnimationHelper(
    val scope: CoroutineScope,
    val appState: AppState,
    val selectedColor: Int,
    val defaultColor: Int,
    val errorColor: Int,
    val successColor: Int
) {

    val text: MutableState<String> = mutableStateOf("")
    val fraction = FloatAnimatable(0f)

    private val argb = ArgbEvaluator()
    private val colorFraction = FloatAnimatable(1f)
    private val statusFraction = FloatAnimatable(0f)
    private var isSuccess: Boolean = false
    private var colorEnterIndex: Int = 0
    private var colorExitIndex: Int = -1

    private var isReverseStatusUpdating: Boolean = false
    var isStatusUpdating: Boolean = false
        private set
    var fractionIndex: Int = 0
        private set
    var scaledText: String = ""
        private set

    fun updateValidationStatus(status: ValidationStatus) {
        if (status != ValidationStatus.NO_RES) {
            isStatusUpdating = true
            isReverseStatusUpdating = false
            isSuccess = status == ValidationStatus.SUCCESS
            animateStatusColor()

            scaledText = text.value
            text.value = ""
            animateScale()
        }
    }

    fun enter(button: Int) {
        if (isStatusUpdating || isReverseStatusUpdating) {
            return
        }

        text.value += button.toString()

        fractionIndex = text.value.length - 1
        colorExitIndex = colorEnterIndex
        colorEnterIndex = fractionIndex + 1

        animateColor()
        animateOffset()
    }

    fun backspace() {
        if (isStatusUpdating || isReverseStatusUpdating || text.value.isEmpty()) {
            return
        }

        fractionIndex = text.value.length - 1
        colorExitIndex = colorEnterIndex
        colorEnterIndex = fractionIndex

        scaledText = text.value
        text.value = text.value.substring(0, fractionIndex)

        if (colorEnterIndex != colorExitIndex) {
            animateColor()
        }
        animateScale()
    }

    private fun animateOffset() {
        scope.launch {
            fraction.snapTo(0f)
            fraction.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = OvershootEasing
                )
            )
        }
    }

    private fun animateScale() {
        scope.launch {
            fraction.snapTo(0f)
            fraction.animateTo(
                targetValue = -1f,
                animationSpec = tween(
                    durationMillis = 150,
                    easing = LinearEasing
                )
            )
        }.invokeOnCompletion {
            scaledText = ""
        }
    }

    private fun getStatusColor(): Int {
        return if (isSuccess) {
            successColor
        } else {
            errorColor
        }
    }

    private fun getTargetColor(index: Int): Int {
        return if (colorEnterIndex == index) {
            selectedColor
        } else {
            defaultColor
        }
    }

    fun colorOf(index: Int): Color {
        if (isReverseStatusUpdating) {
            return Color(
                argb.evaluate(
                    statusFraction.value,
                    getStatusColor(),
                    getTargetColor(index)
                ) as Int
            )
        } else if (isStatusUpdating) {
            return Color(argb.evaluate(statusFraction.value, defaultColor, getStatusColor()) as Int)
        }

        return when (index) {
            colorEnterIndex -> {
                Color(argb.evaluate(colorFraction.value, defaultColor, selectedColor) as Int)
            }
            colorExitIndex -> {
                Color(argb.evaluate(colorFraction.value, selectedColor, defaultColor) as Int)
            }
            else -> {
                Color(defaultColor)
            }
        }
    }

    private fun animateColor() {
        scope.launch {
            colorFraction.snapTo(0f)
            colorFraction.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearEasing
                )
            )
        }
    }

    private fun animateStatusColor() {
        scope.launch {
            statusFraction.snapTo(0f)
            colorFraction.snapTo(1f)
            statusFraction.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearEasing
                )
            )

            if (isStatusUpdating) {
                isReverseStatusUpdating = true
                isStatusUpdating = false
                colorExitIndex = -1
                colorEnterIndex = 0
                animateStatusColor()
            } else if (isReverseStatusUpdating) {
                isReverseStatusUpdating = false
                statusFraction.animateTo(0f)

                if (isSuccess) {
                    appState.navigateTo(Route.LoginUsername)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginValidation() {
    VideoSharingPreview(Route.LoginValidation)
}