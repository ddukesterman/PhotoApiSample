package com.trenton.photogallery.feature.photosearch.data.repository

import com.trenton.photogallery.BuildConfig
import com.trenton.photogallery.feature.photosearch.data.api.PixabayPhotoApiService
import com.trenton.photogallery.feature.photosearch.data.model.PhotoSearchList
import com.trenton.photogallery.feature.photosearch.data.model.toPhotoSearchList
import javax.inject.Inject

class PixabayPhotoRepository @Inject constructor(
    private val apiService: PixabayPhotoApiService,
) : PhotoRepository {
    override suspend fun searchForPhotos(
        keyword: String,
        perPage: Int,
        page: Int
    ): Result<PhotoSearchList> {
        return try {
            val response = apiService.searchForTerm(
                key = BuildConfig.PIXABAY_API_KEY,
                query = keyword,
                perPage = perPage,
                page = page
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toPhotoSearchList())
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("Error: ${response.errorBody()?.toString()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}