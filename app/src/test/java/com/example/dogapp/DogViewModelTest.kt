package com.example.dogapp

import com.example.dogapp.api.DogRepository
import com.example.dogapp.api.DogResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DogViewModelTest {

    @MockK
    private lateinit var dogRepository: DogRepository

    @InjectMockKs
    private lateinit var viewModel: DogViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this)
    }

    @Test
    fun `should return ResultUIState success if the breed is found`() = runTest {

        val searchInput = "hound"

        val dogs = listOf(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_10263.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_10715.jpg"
        )


        coEvery { dogRepository.findAllByBreed(searchInput) } returns Result.success(
            DogResponse(
                "success",
                dogs
            )
        )

        viewModel.searchAllByBreed(searchInput)

        assertEquals(ResultUiState.Success(dogs), viewModel.dogsByBreedList.drop(1).first())

    }

    @Test
    fun `should return ResultUIState failure if the breed it does not exist`() = runTest {

        val invalidSearchInput = "doesNotExist"

        coEvery { dogRepository.findAllByBreed(invalidSearchInput) } returns Result.failure(Exception("Error getting dogs"))

        viewModel.searchAllByBreed(invalidSearchInput)

        assertEquals(ResultUiState.Error, viewModel.dogsByBreedList.drop(1).first())

    }
}