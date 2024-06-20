package com.example.dogapp

sealed interface ResultUiState<out T> {

    data object Loading : ResultUiState<Nothing>

    data object Error : ResultUiState<Nothing>

    data object SuccessButEmpty : ResultUiState<Nothing>

    data class Success(val data: List<String>) : ResultUiState<List<String>>

}
