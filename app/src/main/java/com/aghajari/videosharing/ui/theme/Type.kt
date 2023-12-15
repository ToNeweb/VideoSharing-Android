package com.aghajari.videosharing.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.aghajari.videosharing.R

val videoSharingFont = FontFamily(
    listOf(
        Font(R.font.yekan_bakh, weight = FontWeight.Normal),
        Font(R.font.yekan_bakh_bold, weight = FontWeight.Bold),
        Font(R.font.yekan_bakh_light, weight = FontWeight.Light),
        Font(R.font.yekan_bakh_fat, weight = FontWeight.ExtraBold),
    )
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    bodyMedium = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    bodySmall = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    titleLarge = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    titleMedium = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    titleSmall = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    labelLarge = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    labelMedium = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    labelSmall = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    ),
    headlineLarge = TextStyle(
        fontFamily = videoSharingFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    )
)