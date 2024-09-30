package com.trenton.photogallery.feature.photosearch.data.model

import com.google.gson.annotations.SerializedName

data class PexelPhotoSearchResponse(
    @SerializedName("total_results")
    val totalResults: Int,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<PexelPhoto>,
    @SerializedName("next_page")
    val nextPage: String?,
)

fun PexelPhotoSearchResponse.toPhotoSearchList(): PhotoSearchList  {
    val photos = photos.map {
        PhotoItem(
            id = it.id,
            imageUrl = it.src.medium,
            title = "By: ${it.photographer}",
            apiSource = "Pexel",
        )
    }
    return PhotoSearchList(
        nextPage != null,
        photos,
    )
}

data class PexelPhoto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    @SerializedName("photographer_id")
    val photographerId: Int,
    @SerializedName("avg_color")
    val avgColor: String,
    val src: PexelPhotoSource,
    val liked: Boolean,
    val alt: String,
)

data class PexelPhotoSource(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String,
)
