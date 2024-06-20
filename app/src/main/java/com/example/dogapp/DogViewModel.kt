package com.example.dogapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel(){

    private val _dogsByBreedList: MutableStateFlow<ResultUiState<DogResponse>> =
        MutableStateFlow(ResultUiState.Loading)
    var dogsByBreedList: StateFlow<ResultUiState<DogResponse>> = _dogsByBreedList.asStateFlow()


    fun searchAllByBreed(breed: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.findAllByBreed(breed)

            try {
                if (response.isSuccess) {
                    val dogs = response.getOrNull()
                    if(dogs != null){
                        _dogsByBreedList.value = ResultUiState.Success(dogs)
                    } else{
                        _dogsByBreedList.value = ResultUiState.SuccessButEmpty
                    }
                } else {
                    _dogsByBreedList.value = ResultUiState.Error
                }
            } catch (e: Exception) {
                _dogsByBreedList.value = ResultUiState.Error
            }
        }
    }


}