package com.trenton.photogallery.feature.photosearch.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.trenton.photogallery.feature.photosearch.data.model.PhotoItem
import com.trenton.photogallery.feature.photosearch.ui.viewmodel.PhotoListSearchViewModel
import com.trenton.photogallery.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListSearchScreen(
    viewModel: PhotoListSearchViewModel = hiltViewModel(),
    navController: NavController,
) {
    val photoItems by viewModel.searchResults.collectAsState()

    val showErrorRetry = viewModel.showErrorRetry
    val searchTerm = viewModel.searchTerm
    val showFetchMoreImages = viewModel.showFetchMoreImages

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavDrawerContent { navRoute ->
                coroutineScope.launch {
                    drawerState.close()
                }
                navController.navigate(navRoute)
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        TopBarTitle(
                            searchTerm = searchTerm,
                            recentSearches = viewModel.recentSearches,
                            onTextValueChanged = {
                                viewModel.updateSearchTerm(it)
                            },
                            onKeyboardSearch = {
                                viewModel.searchForResults()
                            },
                            onRecentItemSelected = { recentSearch ->
                                viewModel.updateSearchTerm(recentSearch)
                                viewModel.searchForResults()
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
        ) { parentPadding ->
            PhotoListSearchContent(
                parentPadding = parentPadding,
                photoItems = photoItems,
                showErrorRetry = showErrorRetry,
                showFetchMoreImages = showFetchMoreImages,
                retrySearch = {
                      viewModel.retrySearch()
                },
                onFetchMoreImages = { viewModel.fetchMoreImages() },
                onNavigateToPhotoDetail = { json ->
                    navController.navigate(
                        Screen.PhotoDetail.createRoute(
                            json
                        )
                    )
                },
                onBookmarkSelected = { photoItem ->
                    viewModel.updateBookmarkStatus(photoItem, !photoItem.bookmarked)
                },
            )
        }
    }
}

@Composable
private fun TopBarTitle(
    searchTerm: String,
    recentSearches: SnapshotStateList<String>,
    onTextValueChanged: (textValue: String) -> Unit,
    onRecentItemSelected: (textValue: String) -> Unit,
    onKeyboardSearch: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                expanded = it.isFocused && recentSearches.isNotEmpty()
            },
        value = searchTerm,
        placeholder = { Text("Search Images...") },
        onValueChange = { value ->
            onTextValueChanged(value)
            expanded = value.isNotEmpty() && recentSearches.isNotEmpty()
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onKeyboardSearch()
                expanded = false
                keyboardController?.hide()
            },
        ),
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        properties = PopupProperties(focusable = false)
    ) {
        recentSearches.forEach { recentSearch ->
            DropdownMenuItem(
                text = { Text(text = recentSearch) },
                onClick = {
                    onRecentItemSelected(recentSearch)
                    expanded = false
                    keyboardController?.hide()
                },
            )
        }
    }
}


@Composable
fun FetchMoreImagesItem(onFetchMoreItems: () -> Unit) {
    Text(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onFetchMoreItems() },
        text = "Fetch more images!!"
    )
}

@Composable
fun NavDrawerContent(onDrawerItemSelected: (route: String) -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Gray)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Nav Drawer",
            style = MaterialTheme.typography.headlineSmall,
        )
        Divider()

        NavDrawerItem(
            title = "Settings",
            route = Screen.Settings.route,
            onDrawerItemSelected = onDrawerItemSelected,
        )
        NavDrawerItem(
            title = "Bookmark Photos",
            route = Screen.BookmarkedPhotos.route,
            onDrawerItemSelected = onDrawerItemSelected,
        )
    }
}

@Composable
fun NavDrawerItem(title: String, route: String, onDrawerItemSelected: (route: String) -> Unit) {
    ListItem(
        modifier = Modifier
            .clickable { onDrawerItemSelected(route) },
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoItemView(
    photoItem: PhotoItem,
    onItemSelected: (photoItem: PhotoItem) -> Unit,
    onBookmarkSelected: (photoItem: PhotoItem) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            onItemSelected(photoItem)
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
                    model = photoItem.imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Result Image",
                    onError = {
                        Log.d("PhotoListSearchScreen", "Error loading image ${photoItem.imageUrl}")
                    },
                    error = ColorPainter(Color.Gray)
                )
                Icon(
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .clickable {
                            onBookmarkSelected(photoItem)
                        },
                    imageVector = if (photoItem.bookmarked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    tint = Color.Red,
                    contentDescription = "Bookmark Icon",
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPhotoListSearchScreen() {
    val mockNavController = rememberNavController()
    PhotoListSearchScreen(
        navController = mockNavController
    )
}


@Preview
@Composable
fun PreviewPhotoItemView() {
    PhotoItemView(
        PhotoItem(
            12345,
            "https://www.costco.com",
            "Test Title",
            "Pexel",
        ),
        {},
        {},
    )
}