package com.example.cocktails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.cocktails.ui.components.CocktailScreen
import com.example.cocktails.ui.theme.CocktailTheme

class MainActivity : ComponentActivity() {

    private val viewModel: CocktailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailTheme {
                val uiState by viewModel.uiState.collectAsState()
                Surface(modifier = Modifier.fillMaxSize()) {
                    CocktailScreen(
                        state = uiState,
                        onCocktailSelected = viewModel::onCocktailSelected,
                        onDismissDetails = viewModel::onDismissDetails
                    )
                }
            }
        }
    }
}
