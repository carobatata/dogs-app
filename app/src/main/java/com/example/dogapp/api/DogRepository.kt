package com.example.dogapp.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(private val restApi: RestApiService) {

    suspend fun findAllByBreed(query: String): Result<DogResponse?> {
        return withContext(Dispatchers.IO) {
            try {
                val call = restApi.getDogsByBreed(query)
                val response = call.execute()

                when {
                    response.isSuccessful -> {
                        val dogs = response.body()
                        Result.success(dogs)
                    }

                    else -> {
                        Result.failure(Exception("Error getting dogs"))
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getAllBreeds(): Result<List<String>?> {

        return withContext(Dispatchers.IO) {

            try {
                val call = restApi.getAllBreeds()
                val response = call.execute()

                if (response.isSuccessful) {
                    val breeds = response.body()?.message?.keys?.toList()
                    Result.success(breeds)
                } else {
                    Result.failure(Exception("Error getting breeds"))
                }
            } catch (e: Exception) {
                Result.failure<List<String>>(e)
            }
        }
    }
}