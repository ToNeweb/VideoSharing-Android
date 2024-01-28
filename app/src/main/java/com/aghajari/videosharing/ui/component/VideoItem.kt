package com.aghajari.videosharing.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aghajari.videosharing.R
import com.aghajari.videosharing.model.VideoModel
import com.aghajari.videosharing.ui.theme.VideoSharingTheme

@Composable
fun VideoItem(
    model: VideoModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(
            imageModel = model.image,
            placeholder = R.drawable.video_placeholder,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(412f / 232)
        )
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                imageModel = model.senderProfile,
                placeholder = R.drawable.video_placeholder,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = model.title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = model.description,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun VideoItemPreview() {
    VideoSharingTheme {
        VideoItem(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            model = VideoModel(
                videoID = "",
                videoUrl = "",
                image = "",
                senderName = "",
                senderProfile = "",
                title = "This is title",
                description = "Hello there",
                time = System.currentTimeMillis()
            )
        )
    }
}