package com.example.hugohgil.catsapp.viewmodel

import androidx.paging.PagingData
import com.example.hugohgil.catsapp.data.fakes.FakeBreedRepository
import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.testBreed
import com.example.hugohgil.catsapp.ui.breedlist.BreedListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeBreedRepository: FakeBreedRepository
    private lateinit var breedListViewModel: BreedListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeBreedRepository = FakeBreedRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `toggleFavorite when breed is not favourite should mark as favorite`() = runTest {
        breedListViewModel = BreedListViewModel(fakeBreedRepository)

        val breed = testBreed.copy(isFavorite = false)
        val expected = breed.copy(isFavorite = true)

        breedListViewModel.toggleFavorite(breed)

        testDispatcher.scheduler.advanceUntilIdle()

        assert(fakeBreedRepository.updatedBreeds.contains(expected))
    }

    @Test
    fun `breeds emits flow from repository`() = runTest {
        val pagingData = PagingData.from(listOf(testBreed))
        fakeBreedRepository.breedFlow = flowOf(pagingData)

        breedListViewModel = BreedListViewModel(fakeBreedRepository)

        val collected = mutableListOf<PagingData<Breed>>()
        val job = launch {
            breedListViewModel.breeds.collect { collected.add(it) }
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assert(collected.isNotEmpty())
        job.cancel()
    }
}