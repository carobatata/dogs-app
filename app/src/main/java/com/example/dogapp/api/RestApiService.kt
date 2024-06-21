package com.example.dogapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApiService {
    @GET("breed/{breed}/images")
    fun getDogsByBreed(@Path("breed") breed: String): Call<DogResponse>

    @GET("breeds/list/all")
    fun getAllBreeds(): Call<BreedsResponse>


}