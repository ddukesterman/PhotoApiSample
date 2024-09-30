package com.trenton.photogallery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.trenton.photogallery.feature.bookmark.ui.screen.BookmarkedPhotosScreen
import com.trenton.photogallery.feature.photosearch.data.model.PhotoItem
import com.trenton.photogallery.feature.photosearch.ui.screen.PhotoDetailScreen
import com.trenton.photogallery.feature.photosearch.ui.screen.PhotoListSearchScreen
import com.trenton.photogallery.feature.settings.ui.screen.SettingsScreen
import kotlinx.serialization.json.Json

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PhotoList.route,
    ) {
        composable(Screen.PhotoList.route) {
            PhotoListSearchScreen(navController = navController)
        }
        composable(
            route = Screen.PhotoDetail.route,
            arguments = listOf(navArgument("photo") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val photoJSON = backStackEntry.arguments?.getString("photo")
            val photoItem = photoJSON?.let {
                Json.decodeFromString<PhotoItem>(it)
            }
            PhotoDetailScreen(photoItem = photoItem)
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.BookmarkedPhotos.route) {
            BookmarkedPhotosScreen(navController = navController)
        }
    }
}
