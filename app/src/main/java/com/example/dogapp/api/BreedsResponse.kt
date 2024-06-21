package com.example.dogapp.api

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)