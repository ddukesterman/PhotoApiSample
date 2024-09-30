package com.trenton.photogallery.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object PhotoList : Screen("PhotoListSearchScreen")
    data object PhotoDetail : Screen("PhotoDetailScreen/{photo}") {
        fun createRoute(photoItemJson: String): String {
            val encodedPhotoItemJson = Uri.encode(photoItemJson)
            return "PhotoDetailScreen/$encodedPhotoItemJson"
        }
    }
    data object Settings : Screen("Settings")
    data object BookmarkedPhotos : Screen("BookmarkPhotos")
}