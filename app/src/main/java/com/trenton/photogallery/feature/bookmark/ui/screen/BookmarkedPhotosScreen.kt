package com.trenton.photogallery.feature.bookmark.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.trenton.photogallery.feature.bookmark.data.model.BookmarkedPhoto
import com.trenton.photogallery.feature.bookmark.ui.viewmodel.BookmarkedPhotosViewModel
import com.trenton.photogallery.feature.photosearch.data.model.PhotoItem
import com.trenton.photogallery.navigation.Screen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun BookmarkedPhotosScreen(
    viewModel: BookmarkedPhotosViewModel = hiltViewModel(),
    navController: NavController,
) {
    val bookmarkedPhotos by viewModel.bookmarkedPhotos.collectAsState()

    if(bookmarkedPhotos.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
        ) {
            items(bookmarkedPhotos.size) { index ->
                PhotoItemView(
                    bookmarkedPhoto = bookmarkedPhotos[index],
                    onItemSelected = { photoItem ->
                        val photoItemJson = Json.encodeToString(photoItem.toPhotoItem())
                        navController.navigate(Screen.PhotoDetail.createRoute(photoItemJson))
                    },
                    onBookmarkSelected = {
                        viewModel.removeBookmark(it)
                    },
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "No bookmarked photos... Do some searching!!")
        }
    }
}

private fun BookmarkedPhoto.toPhotoItem(): PhotoItem {
    return PhotoItem(
        id = id,
        imageUrl = imageUrl,
        title = title,
        apiSource = apiSource,
        bookmarked = true,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoItemView(
    bookmarkedPhoto: BookmarkedPhoto,
    onItemSelected: (bookmarkedPhoto: BookmarkedPhoto) -> Unit,
    onBookmarkSelected: (bookmarkedPhoto: BookmarkedPhoto) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            onItemSelected(bookmarkedPhoto)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = bookmarkedPhoto.imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Bookmarked Image",
                    onError = {
                        Log.d(
                            "BookmarkedPhotoScreen",
                            "Error loading image ${bookmarkedPhoto.imageUrl}"
                        )
                    },
                    error = ColorPainter(Color.Gray)
                )
                Icon(
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .clickable {
                            onBookmarkSelected(bookmarkedPhoto)
                        },
                    imageVector = Icons.Filled.Favorite,
                    tint = Color.Red,
                    contentDescription = "Bookmark Icon",
                )
            }
        }
    }
}