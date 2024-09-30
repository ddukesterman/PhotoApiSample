package com.trenton.photogallery.feature.bookmark.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trenton.photogallery.feature.bookmark.data.model.BookmarkedPhoto
import kotlinx.coroutines.flow.Flow

const val BOOKMARK_PHOTO_DATABASE = "bookmarked_photos"

@Dao
interface BookmarkPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkedPhoto(bookmarkedPhoto: BookmarkedPhoto)

    @Query("SELECT * FROM $BOOKMARK_PHOTO_DATABASE")
    fun getAllBookmarkedPhotos(): Flow<List<BookmarkedPhoto>>

    @Query("SELECT EXISTS(SELECT 1 FROM $BOOKMARK_PHOTO_DATABASE WHERE id = :id AND apiSource = :apiSource)")
    suspend fun photoExists(id: String, apiSource: String): Boolean

    @Delete
    suspend fun deleteBookmark(bookmarkedPhoto: BookmarkedPhoto)

}