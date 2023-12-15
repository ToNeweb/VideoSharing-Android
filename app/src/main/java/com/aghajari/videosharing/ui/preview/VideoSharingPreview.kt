package com.aghajari.videosharing.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.aghajari.videosharing.R
import com.aghajari.videosharing.nav.Route
import com.aghajari.videosharing.ui.component.Image
import com.aghajari.videosharing.ui.theme.VideoSharingTheme

@Composable
fun VideoSharingPreview(route: Route) {
    VideoSharingTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageModel = R.drawable.background,
                contentScale = ContentScale.Crop
            )
            route.content.invoke()
        }
    }
}