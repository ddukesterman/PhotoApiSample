package com.trenton.photogallery.feature.photosearch.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.trenton.photogallery.feature.photosearch.data.model.PhotoItem

@Composable
fun PhotoDetailScreen(
    photoItem: PhotoItem?
) {
    if (photoItem != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = photoItem.imageUrl,
                contentScale = ContentScale.Fit,
                contentDescription = "Result Image",
                onError = {
                    Log.d("PhotoListSearchScreen", "Error loading image ${photoItem.imageUrl}")
                },
                error = ColorPainter(Color.Gray)
            )
            Text(
                text = photoItem.title,
            )
            Text(text = "API: ${photoItem.apiSource}")
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Error loading image...")
        }
    }
}