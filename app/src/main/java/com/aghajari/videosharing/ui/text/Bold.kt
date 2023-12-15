package com.aghajari.videosharing.ui.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.aghajari.videosharing.ui.theme.videoSharingFont

fun AnnotatedString.Builder.bold(text: String) {
    withStyle(
        SpanStyle(
            fontFamily = videoSharingFont,
            fontWeight = FontWeight.Bold
        )
    ) {
        append(text)
    }
}