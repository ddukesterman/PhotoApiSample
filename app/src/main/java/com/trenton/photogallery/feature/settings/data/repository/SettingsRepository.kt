package com.trenton.photogallery.feature.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.trenton.photogallery.feature.settings.data.model.PhotoApiSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val photoApiSourceKey = stringPreferencesKey("photo_api_source")

    val apiSource: Flow<PhotoApiSource> = dataStore.data
        .map { pref ->
            val source = pref[photoApiSourceKey] ?: PhotoApiSource.PEXELS.name
            PhotoApiSource.valueOf(source)
        }

    suspend fun savePhotoApiSource(source: PhotoApiSource) {
        dataStore.edit { pref ->
            pref[photoApiSourceKey] = source.name
        }
    }


}