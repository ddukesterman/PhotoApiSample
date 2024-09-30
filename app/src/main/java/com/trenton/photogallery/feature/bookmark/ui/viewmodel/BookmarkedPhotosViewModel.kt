package com.trenton.photogallery.feature.bookmark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trenton.photogallery.feature.bookmark.data.model.BookmarkedPhoto
import com.trenton.photogallery.feature.bookmark.data.repository.BookmarkPhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkedPhotosViewModel @Inject constructor(
    private val bookmarkPhotoRepository: BookmarkPhotoRepository
): ViewModel() {

    private val _bookmarkedPhotos = MutableStateFlow<List<BookmarkedPhoto>>(emptyList())
    val bookmarkedPhotos: StateFlow<List<BookmarkedPhoto>> = _bookmarkedPhotos

    init {
        viewModelScope.launch {
            bookmarkPhotoRepository.getPhotos().collect{
                _bookmarkedPhotos.value = it
            }
        }
    }

    private suspend fun deleteBookmark(bookmarkedPhoto: BookmarkedPhoto) {
        bookmarkPhotoRepository.deleteBookmark(bookmarkedPhoto)
    }

    fun removeBookmark(bookmarkedPhoto: BookmarkedPhoto) {
        viewModelScope.launch {
            deleteBookmark(bookmarkedPhoto)
        }
    }

}