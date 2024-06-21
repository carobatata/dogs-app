package com.example.dogapp

import com.example.dogapp.api.DogRepository
import com.example.dogapp.api.DogResponse
import com.example.dogapp.api.RestApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class DogRepositoryTest{

    @MockK
    private lateinit var restApi: RestApiService

    @InjectMockKs
    private lateinit var dogRepository: DogRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should find all dogs by given breed`() {

        val houndDogsImages = listOf(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_10263.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_10715.jpg"
        )

        val call = mockk<Call<DogResponse>>()

        coEvery { restApi.getDogsByBreed("hound") } returns call
        coEvery { call.execute() } returns Response.success(DogResponse("success", houndDogsImages))

        runBlocking {
            val result = dogRepository.findAllByBreed("hound")

            assertEquals(result, Result.success(DogResponse("success", houndDogsImages)))
        }
    }

    @Test
    fun `should return failure if breed does not exist`() {

        val call = mockk<Call<DogResponse>>()
        val exception = Exception("Error getting dogs")

        coEvery { restApi.getDogsByBreed("doesNotExist") } returns call
        coEvery { call.execute() } throws exception

        runBlocking {
            val result = dogRepository.findAllByBreed("doesNotExist")

            assertEquals(Result.failure<Exception>(exception), result)
        }
    }
}