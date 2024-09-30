package com.trenton.photogallery.feature.photosearch.data.model

data class PixabayPhotoSearchResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>
)

fun PixabayPhotoSearchResponse.toPhotoSearchList(): PhotoSearchList  {
    val photos = hits.map {
        PhotoItem(
            id = it.id,
            imageUrl = it.largeImageURL,
            title = "By: ${it.user}",
            apiSource = "Pixabay"
        )
    }
    //TODO Fix for showing additional results
    return PhotoSearchList(
        true,
        photos,
    )
}

data class Hit(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    val largeImageURL: String,
    val fullHDURL: String,
    val imageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val user_id: Int,
    val user: String,
    val userImageURL: String
)