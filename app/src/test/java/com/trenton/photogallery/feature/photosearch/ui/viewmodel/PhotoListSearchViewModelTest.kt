package com.trenton.photogallery.feature.photosearch.ui.viewmodel

import com.trenton.photogallery.feature.bookmark.data.repository.BookmarkPhotoRepository
import com.trenton.photogallery.feature.photosearch.data.model.PhotoItem
import com.trenton.photogallery.feature.photosearch.data.model.PhotoSearchList
import com.trenton.photogallery.feature.photosearch.data.repository.PexelPhotoRepository
import com.trenton.photogallery.feature.photosearch.data.repository.PixabayPhotoRepository
import com.trenton.photogallery.feature.settings.data.model.PhotoApiSource
import com.trenton.photogallery.feature.settings.data.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoListSearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PhotoListSearchViewModel
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var bookmarkPhotoRepository: BookmarkPhotoRepository
    private lateinit var pexelPhotoRepository: PexelPhotoRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        pexelPhotoRepository = mockk(relaxed = true)
        bookmarkPhotoRepository = mockk(relaxed = true)
        settingsRepository = mockk(relaxed = true)
        viewModel = getStubbedViewModel()
    }

    private fun getStubbedViewModel() = PhotoListSearchViewModel(
        pexelPhotoRepository = pexelPhotoRepository,
        pixabayPhotoRepository = mockk<PixabayPhotoRepository>(),
        settingsRepository = settingsRepository,
        bookmarkPhotoRepository = bookmarkPhotoRepository,
    )

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify search term update`() = runTest {
        val expectingSource = PhotoApiSource.PEXELS
        coEvery { settingsRepository.apiSource } returns flow { emit(expectingSource) }

        viewModel.updateSearchTerm("New Search")

        advanceUntilIdle()

        assertEquals("New Search", viewModel.searchTerm)
    }

    @Test
    fun `verify page increments`() = runTest {
        coEvery {
            pexelPhotoRepository.searchForPhotos(
                any(),
                any(),
                any()
            )
        } returns Result.success(
            PhotoSearchList(
                true,
                listOf(
                    PhotoItem(
                        id = Random.nextInt(),
                        imageUrl = "google.com",
                        title = "Title",
                        apiSource = PhotoApiSource.PEXELS.name,
                        bookmarked = false,
                    )
                )
            )
        )

        viewModel.fetchMoreImages()

        advanceUntilIdle()

        val currentPage = viewModel.getCurrentPageForTesting()

        assertEquals(2, currentPage)
    }
}