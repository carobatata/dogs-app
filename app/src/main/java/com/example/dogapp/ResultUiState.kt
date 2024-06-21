package com.example.dogapp

sealed interface ResultUiState<out T> {

    data object Start : ResultUiState<Nothing>

    data object Loading : ResultUiState<Nothing>

    data object Error : ResultUiState<Nothing>

    data class Success(val data: List<String>) : ResultUiState<List<String>>

}
