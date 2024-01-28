package com.aghajari.videosharing.model

data class VideoModel(
    val videoID: String,
    val image: String,
    val videoUrl: String,
    val senderName: String,
    val senderProfile: String,
    val title: String,
    val description: String,
    val time: Long
)