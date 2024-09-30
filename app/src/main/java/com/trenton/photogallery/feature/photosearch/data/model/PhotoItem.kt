package com.trenton.photogallery.feature.photosearch.data.model

import kotlinx.serialization.Serializable

// See java class PhotoSearchList
//@Serializable
//data class PhotoSearchList(
//    val hasMoreResults: Boolean,
//    val photoItemList: List<PhotoItem>,
//)

@Serializable
data class PhotoItem(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val apiSource: String,
    var bookmarked: Boolean = false,
)
