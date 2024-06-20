package com.example.dogapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("{breed}/images")
    fun getDogsByBreed(@Path("breed") breed: String): Call<DogResponse>
}