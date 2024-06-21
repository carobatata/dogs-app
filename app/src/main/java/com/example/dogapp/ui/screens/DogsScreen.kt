package com.example.dogapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dogapp.DogViewModel
import com.example.dogapp.ResultUiState
import com.example.dogapp.ui.theme.DogAppTheme

@Composable
fun DogsScreen(
    viewModel: DogViewModel = hiltViewModel()
) {

    val searchInput by viewModel.searchInput.collectAsState()
    val dogsUiState by viewModel.dogsByBreedList.collectAsState()
    val isSearchingByBreed by viewModel.isSearchingByBreed.collectAsState()
    val breedsList by viewModel.breedsList.collectAsState()

    Column(Modifier.padding(16.dp)) {
        TheDogsScreen(
            searchInput,
            viewModel::onSearchTextChange,
            viewModel::searchAllByBreed,
            isSearchingByBreed,
            viewModel::onToggleSearch,
            breedsList,
            dogsUiState
        )
    }
}

@Composable
fun TheDogsScreen(
    searchInput: String,
    onSearchTextChange: (String) -> Unit,
    onSearchByBreed: (String) -> Unit,
    isSearchingByBreed: Boolean,
    onToggleSearch: () -> Unit,
    breedsList: List<String>,
    dogsUiState: ResultUiState<List<String>>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SearchSection(
            searchInput,
            onSearchTextChange,
            onSearchByBreed,
            isSearchingByBreed,
            onToggleSearch,
            breedsList
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
    DogsListSection(dogsUiState)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(
    searchInput: String,
    onSearchTextChange: (String) -> Unit,
    onSearchByBreed: (String) -> Unit,
    isSearchingByBreed: Boolean,
    onToggleSearch: () -> Unit,
    breedsList: List<String>
) {
    SearchBar(query = searchInput,
        onQueryChange = onSearchTextChange,
        onSearch = { onSearchByBreed(searchInput) },
        active = isSearchingByBreed,
        onActiveChange = { onToggleSearch() },
        placeholder = { Text("Type the breed...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Close",
                tint = Color.LightGray
            )
        }
    ) {

        LazyColumn {
            items(breedsList.size) {
                Text(
                    text = breedsList[it],
                    modifier = Modifier.padding(8.dp),
                )
            }

        }

    }
}


@Composable
fun DogsListSection(dogsUiState: ResultUiState<List<String>>) {
    when (dogsUiState) {
        is ResultUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dogsUiState.data.size) {
                    AsyncImage(
                        model = dogsUiState.data[it],
                        contentDescription = "Dog image"
                    )
                }
            }
        }

        is ResultUiState.Error -> {
            Text(text = "Error")
        }

        is ResultUiState.Loading -> {
            Text(text = "Loading")
        }

        is ResultUiState.SuccessButEmpty -> {
            Text(text = "No dogs found")
        }

        ResultUiState.Start -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun DogsScreenPreview() {
    DogAppTheme {
        TheDogsScreen(
            searchInput = "Husky",
            onSearchTextChange = {},
            onSearchByBreed = {},
            isSearchingByBreed = false,
            onToggleSearch = {},
            breedsList = listOf("Husky", "Pug", "Labrador"),
            dogsUiState = ResultUiState.Success(listOf("https://images.dog.ceo/breeds/husky/n02110185_10047.jpg"))
        )
    }
}
