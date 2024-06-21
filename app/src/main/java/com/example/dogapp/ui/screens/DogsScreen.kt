package com.example.dogapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dogapp.ResultUiState
import com.example.dogapp.ui.theme.DogAppTheme

@Composable
fun DogsScreen(onSearch: (String) -> Unit, dogsUiState: ResultUiState<List<String>>) {

    var searchInput by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = searchInput,
                onValueChange = { searchInput = it },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedButton(
                onClick = { onSearch(searchInput) },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(text = "Search")
            }
        }
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
}

@Preview(showBackground = true)
@Composable
fun DogsScreenPreview() {
    DogAppTheme {
        DogsScreen(
            {},
            ResultUiState.Success(
                listOf(
                    "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
                    "https://images.dog.ceo/breeds/hound-afghan/n02088094_10263.jpg",
                    "https://images.dog.ceo/breeds/hound-afghan/n02088094_10715.jpg",
                    "https://images.dog.ceo/breeds/hound-afghan/n02088094_10822.jpg"
                )
            )
        )
    }
}
