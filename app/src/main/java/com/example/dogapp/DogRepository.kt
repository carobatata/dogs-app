package com.example.dogapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(private val api: APIService){

    suspend fun findAllByBreed(query: String): Result<DogResponse?> {
        return withContext(Dispatchers.IO) {
            try {
                val call = api.getDogsByBreed(query)
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