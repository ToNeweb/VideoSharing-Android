package com.aghajari.videosharing

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.aghajari.videosharing.nav.LocalAppState
import com.aghajari.videosharing.nav.Navigation
import com.aghajari.videosharing.nav.rememberAppState
import com.aghajari.videosharing.ui.component.Image
import com.aghajari.videosharing.ui.theme.VideoSharingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        super.onCreate(savedInstanceState)
        setContent {
            VideoSharingTheme {
                CompositionLocalProvider(
                    LocalAppState provides rememberAppState()
                ) {
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
                        Navigation()
                    }
                }
            }
        }
    }
}