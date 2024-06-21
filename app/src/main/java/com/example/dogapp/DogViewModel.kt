package com.example.dogapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.api.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel(){


    private val breeds = listOf("australian", "bulldog", "hound", "pug", "retriever", "setter", "sheepdog", "spaniel", "terrier", "wolfhound")

    private val _dogsByBreedList: MutableStateFlow<ResultUiState<List<String>>> =
        MutableStateFlow(ResultUiState.Start)
    var dogsByBreedList: StateFlow<ResultUiState<List<String>>> = _dogsByBreedList.asStateFlow()

    private val _isSearchingByBreed = MutableStateFlow(false)
    val isSearchingByBreed: StateFlow<Boolean> = _isSearchingByBreed.asStateFlow()

    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()

    private val _breedsList = MutableStateFlow(breeds)
    val breedsList = searchInput.combine(_breedsList) { query, breeds ->
        if(query.isBlank()) {
            return@combine breeds
        }
        breeds.filter { breed ->
            breed.uppercase().contains(query.trim().uppercase())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _breedsList.value
    )


    fun onSearchTextChange(text: String) {
        _searchInput.value = text
    }

    fun onToggleSearch() {
        _isSearchingByBreed.value = !_isSearchingByBreed.value
        if (!_isSearchingByBreed.value) {
            onSearchTextChange("")
        }
    }

    fun searchAllByBreed(breed: String) {

        onToggleSearch()
        _dogsByBreedList.value = ResultUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.findAllByBreed(breed)

            try {
                if (response.isSuccess) {
                    val dogs = response.getOrNull()
                    if(dogs != null){
                        _dogsByBreedList.value = ResultUiState.Success(dogs.images)
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