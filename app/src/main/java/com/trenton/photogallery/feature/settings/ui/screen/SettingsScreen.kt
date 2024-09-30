package com.trenton.photogallery.feature.settings.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trenton.photogallery.feature.settings.data.model.PhotoApiSource
import com.trenton.photogallery.feature.settings.viewmodel.SettingsViewModel
import java.util.Locale

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val selectedApiSource by settingsViewModel.selectedApiSourceFlow.collectAsState(initial = PhotoApiSource.PEXELS)

    SettingsContent(
        selectedApiService = selectedApiSource,
        onApiServiceSelected = { settingsViewModel.updateSelectedApiSource(it) },
    )
}

@Composable
fun SettingsContent(
    selectedApiService: PhotoApiSource,
    onApiServiceSelected: (apiSource: PhotoApiSource) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Image API Service",
            style = MaterialTheme.typography.titleSmall,
        )

        ApiSourceRadioRow(
            apiSource = PhotoApiSource.PEXELS,
            selected = selectedApiService == PhotoApiSource.PEXELS,
            onApiServiceSelected = onApiServiceSelected,
        )

        Divider()

        ApiSourceRadioRow(
            apiSource = PhotoApiSource.PIXABAY,
            selected = selectedApiService == PhotoApiSource.PIXABAY,
            onApiServiceSelected = onApiServiceSelected,
        )

        Divider()
    }
}

@Composable
private fun ApiSourceRadioRow(
    apiSource: PhotoApiSource,
    selected: Boolean,
    onApiServiceSelected: (apiSource: PhotoApiSource) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.testTag("radio_${apiSource.name}"),
            selected = selected,
            onClick = { onApiServiceSelected(apiSource) },
        )
        Text(
            text = apiSource.name.capitalizeFirstLetter(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

private fun String.capitalizeFirstLetter(): String {
    return lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsContent(
        selectedApiService = PhotoApiSource.PEXELS,
        onApiServiceSelected = {},
    )
}