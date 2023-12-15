package com.aghajari.videosharing.ui.animation

import androidx.compose.animation.core.Easing

val OvershootEasing = Easing {
    val tension = 2f
    val t = it - 1f
    t * t * ((tension + 1) * t + tension) + 1.0f
}