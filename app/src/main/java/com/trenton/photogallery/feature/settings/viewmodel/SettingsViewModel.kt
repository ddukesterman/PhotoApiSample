package com.trenton.photogallery.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trenton.photogallery.feature.settings.data.model.PhotoApiSource
import com.trenton.photogallery.feature.settings.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val selectedApiSourceFlow = settingsRepository.apiSource

    fun updateSelectedApiSource(source: PhotoApiSource) {
        viewModelScope.launch {
            settingsRepository.savePhotoApiSource(source)
        }
    }
}