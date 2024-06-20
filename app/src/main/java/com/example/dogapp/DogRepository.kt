package com.example.dogapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(){

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun findAllByBreed(query: String): Result<DogResponse?> {
        return withContext(Dispatchers.IO) {
            try {
                val call = getRetrofit().create(APIService::class.java).getDogsByBreed(query)
                val response = call.execute()

                when {
                    response.isSuccessful -> {
                        val dogs = response.body()
                        Result.success(dogs)                    }
                    else -> {
                        Result.failure(Exception("Error getting dogs"))
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}