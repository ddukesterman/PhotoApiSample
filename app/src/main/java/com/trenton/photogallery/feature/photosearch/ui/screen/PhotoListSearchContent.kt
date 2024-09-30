package com.trenton.photogallery.feature.photosearch.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.trenton.photogallery.feature.photosearch.data.model.PhotoItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun PhotoListSearchContent(
    parentPadding: PaddingValues,
    photoItems: List<PhotoItem>,
    showFetchMoreImages: Boolean,
    showErrorRetry: Boolean,
    onFetchMoreImages: () -> Unit,
    onNavigateToPhotoDetail: (photoJson: String) -> Unit,
    retrySearch: () -> Unit,
    onBookmarkSelected: (photoItem: PhotoItem) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(parentPadding)
            .fillMaxHeight()
    ) {
        if (showErrorRetry) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable {
                        retrySearch()
                    },
                textAlign = TextAlign.Center,
                text = "Tap here to retry",
                style = MaterialTheme.typography.titleLarge,
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                items(photoItems.size) { index ->
                    PhotoItemView(
                        photoItem = photoItems[index],
                        onItemSelected = { photoItem ->
                            val photoItemJson = Json.encodeToString(photoItem)
                            onNavigateToPhotoDetail(photoItemJson)
                        },
                        onBookmarkSelected = onBookmarkSelected,
                    )
                }
                if (showFetchMoreImages) {
                    item {
                        FetchMoreImagesItem { onFetchMoreImages() }
                    }
                }
            }
        }

    }
}