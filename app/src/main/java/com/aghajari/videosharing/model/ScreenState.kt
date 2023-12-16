package com.aghajari.videosharing.model

enum class ScreenState {
    LOADING,
    SUCCESS,
    ERROR,
    EMPTY
}

fun ScreenState.hasResponse() =
    this == ScreenState.SUCCESS || this == ScreenState.ERROR