package com.trenton.photogallery.feature.photosearch.data.repository

import com.trenton.photogallery.feature.photosearch.data.model.PhotoSearchList

interface PhotoRepository {

    suspend fun searchForPhotos(keyword: String, perPage: Int = 25, page: Int = 1): Result<PhotoSearchList>

}