package com.trenton.photogallery.feature.settings.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.trenton.photogallery.feature.settings.data.model.PhotoApiSource
import org.junit.Rule
import org.junit.Test


class SettingsScreenComposeUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settingsContent() {
        var selectedApiSource by mutableStateOf(PhotoApiSource.PEXELS)

        composeTestRule.setContent {
            SettingsContent(
                selectedApiService = selectedApiSource,
                onApiServiceSelected = {
                    selectedApiSource = it
                },
            )
        }

        val pexelsRadioTag = "radio_${PhotoApiSource.PEXELS.name}"
        val pixabayRadioTag = "radio_${PhotoApiSource.PIXABAY.name}"

        composeTestRule.onNodeWithTag(pexelsRadioTag).assertIsSelected()
        composeTestRule.onNodeWithTag(pixabayRadioTag).assertIsNotSelected()

        composeTestRule.onNodeWithTag(pixabayRadioTag).performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(pexelsRadioTag).assertIsNotSelected()
        composeTestRule.onNodeWithTag(pixabayRadioTag).assertIsSelected()
    }
}