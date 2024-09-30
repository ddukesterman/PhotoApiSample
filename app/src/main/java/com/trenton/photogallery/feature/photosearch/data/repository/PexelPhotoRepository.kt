package com.trenton.photogallery.feature.photosearch.data.repository

import com.trenton.photogallery.feature.photosearch.data.api.PexelPhotoApiService
import com.trenton.photogallery.feature.photosearch.data.model.PhotoSearchList
import com.trenton.photogallery.feature.photosearch.data.model.toPhotoSearchList
import javax.inject.Inject
import kotlin.random.Random

class PexelPhotoRepository @Inject constructor(
    private val apiService: PexelPhotoApiService,
) : PhotoRepository {

    override suspend fun searchForPhotos(
        keyword: String,
        perPage: Int,
        page: Int
    ): Result<PhotoSearchList> {
        return try {
            val response = apiService.searchForTerm(
                keyword,
                perPage,
                page,
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