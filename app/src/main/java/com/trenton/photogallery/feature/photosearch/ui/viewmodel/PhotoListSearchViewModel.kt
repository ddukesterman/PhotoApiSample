package com.trenton.photogallery.feature.photosearch.ui.viewmodel

import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trenton.photogallery.feature.bookmark.data.model.BookmarkedPhoto
import com.trenton.photogallery.feature.bookmark.data.repository.BookmarkPhotoRepository
import com.trenton.photogallery.feature.photosearch.data.model.PhotoItem
import com.trenton.photogallery.feature.photosearch.data.repository.PexelPhotoRepository
import com.trenton.photogallery.feature.photosearch.data.repository.PixabayPhotoRepository
import com.trenton.photogallery.feature.settings.data.model.PhotoApiSource
import com.trenton.photogallery.feature.settings.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PhotoListSearchViewModel @Inject constructor(
    private val pexelPhotoRepository: PexelPhotoRepository,
    private val pixabayPhotoRepository: PixabayPhotoRepository,
    private val settingsRepository: SettingsRepository,
    private val bookmarkPhotoRepository: BookmarkPhotoRepository
) : ViewModel() {

    protected var currentPage = 1

    var recentSearches = mutableStateListOf<String>()
        private set

    var showFetchMoreImages by mutableStateOf(false)
        private set

    var showErrorRetry by mutableStateOf(false)
        private set

    var searchTerm by mutableStateOf("")
        private set

    private val _searchResults = MutableStateFlow(listOf<PhotoItem>())
    val searchResults = _searchResults.asStateFlow()

    private var _apiSource: PhotoApiSource = PhotoApiSource.PEXELS

    init {
        viewModelScope.launch {
            settingsRepository.apiSource.collect {
                _apiSource = it
                currentPage = 1
                showFetchMoreImages = false
                _searchResults.emit(listOf())
            }
        }
    }

    fun updateSearchTerm(newTerm: String) {
        viewModelScope.launch {
            searchTerm = newTerm
        }
    }

    fun searchForResults() {
        currentPage = 1
        showErrorRetry = false
        viewModelScope.launch {
            if (searchTerm.isNotEmpty()) {
                if (searchTerm in recentSearches) {
                    recentSearches.remove(searchTerm)
                }

                recentSearches.add(0, searchTerm)
            }
            fetchPhotos()
        }
    }

    private suspend fun fetchPhotos(currentList: List<PhotoItem> = listOf()) {
        val results = when (_apiSource) {
            PhotoApiSource.PEXELS -> {
                pexelPhotoRepository.searchForPhotos(
                    keyword = searchTerm,
                    page = currentPage,
                )
            }

            else -> {
                pixabayPhotoRepository.searchForPhotos(
                    keyword = searchTerm,
                    page = currentPage
                )
            }
        }

        results.fold(
            onSuccess = { photoList ->
                val updateBookmarkValues = photoList.photoItemList.map {
                    if(bookmarkPhotoRepository.contains(it.id.toString(), it.apiSource)) {
                        it.copy(bookmarked = true)
                    } else {
                        it
                    }
                }
                showFetchMoreImages = photoList.isHasMoreResults
                _searchResults.emit(currentList + updateBookmarkValues)
            },
            onFailure = { error ->
                showErrorRetry = true
            },
        )
    }

    fun fetchMoreImages() {
        currentPage++
        viewModelScope.launch {
            fetchPhotos(_searchResults.value)
        }
    }

    fun retrySearch() {
        showErrorRetry = false
        viewModelScope.launch {
            if (searchTerm.isNotEmpty()) {
                if (searchTerm in recentSearches) {
                    recentSearches.remove(searchTerm)
                }

                recentSearches.add(0, searchTerm)
            }
            fetchPhotos(_searchResults.value)
        }
    }

    fun updateBookmarkStatus(photoItem: PhotoItem, newBookmarkStatus: Boolean) {
        viewModelScope.launch {
            if (newBookmarkStatus) {
                bookmarkPhotoRepository.addBookmark(photoItem.toBookmarkItem())
            } else {
                bookmarkPhotoRepository.deleteBookmark(photoItem.toBookmarkItem())
            }

            val updatedResults = _searchResults.value.map {
                if (it.id == photoItem.id) {
                    it.copy(bookmarked = newBookmarkStatus)
                } else {
                    it
                }
            }

            _searchResults.emit(updatedResults)
        }
    }

    //Testing function towards the bottom
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getCurrentPageForTesting(): Int = currentPage
}

private fun PhotoItem.toBookmarkItem(): BookmarkedPhoto {
    return BookmarkedPhoto(
        id = id,
        imageUrl = imageUrl,
        title = title,
        apiSource = apiSource,
    )
}
