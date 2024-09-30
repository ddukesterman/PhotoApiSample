package com.trenton.photogallery.feature.bookmark.data.repository

import com.trenton.photogallery.feature.bookmark.data.database.BookmarkPhotoDao
import com.trenton.photogallery.feature.bookmark.data.model.BookmarkedPhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkPhotoRepository @Inject constructor(
    private val bookmarkDao: BookmarkPhotoDao,
) {

    suspend fun addBookmark(bookmarkedPhoto: BookmarkedPhoto) {
        bookmarkDao.insertBookmarkedPhoto(bookmarkedPhoto)
    }

    fun getPhotos(): Flow<List<BookmarkedPhoto>> {
        return bookmarkDao.getAllBookmarkedPhotos()
    }

    suspend fun contains(id: String, apiSource: String): Boolean {
        return bookmarkDao.photoExists(id, apiSource)
    }

    suspend fun deleteBookmark(bookmarkedPhoto: BookmarkedPhoto) {
        bookmarkDao.deleteBookmark(bookmarkedPhoto)
    }

}