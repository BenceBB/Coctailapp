package com.example.cocktails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.Cocktail
import com.example.cocktails.data.CocktailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CocktailRepository(application)

    private val _uiState = MutableStateFlow(CocktailUiState())
    val uiState: StateFlow<CocktailUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val cocktails = repository.loadCocktails()
            _uiState.update { state ->
                state.copy(cocktails = cocktails, isLoading = false)
            }
        }
    }

    fun onCocktailSelected(cocktail: Cocktail) {
        _uiState.update { state ->
            state.copy(selectedCocktail = cocktail)
        }
    }

    fun onDismissDetails() {
        _uiState.update { state ->
            state.copy(selectedCocktail = null)
        }
    }
}

data class CocktailUiState(
    val cocktails: List<Cocktail> = emptyList(),
    val isLoading: Boolean = true,
    val selectedCocktail: Cocktail? = null
)
