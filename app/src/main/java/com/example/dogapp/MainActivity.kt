package com.example.dogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dogapp.ui.screens.DogsScreen
import com.example.dogapp.ui.screens.IntroScreen
import com.example.dogapp.ui.theme.DogAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navigationController = rememberNavController()

            DogAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.IntroScreen.name
                        ) {
                        composable(Routes.IntroScreen.name) {
                            IntroScreen { navigationController.navigate(Routes.DogsScreen.name) }
                        }
                        composable(Routes.DogsScreen.name) {
                            DogsScreen()
                        }

                    }
                }
            }
        }
    }
}


