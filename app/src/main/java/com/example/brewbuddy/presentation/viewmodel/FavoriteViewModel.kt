package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.usecase.favorites.AddFavoriteUseCase
import com.example.brewbuddy.domain.usecase.favorites.GetFavoriteUseCase
import com.example.brewbuddy.domain.usecase.favorites.RemoveFavoriteUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    val favorites: StateFlow<List<Favorite>> = getFavoritesUseCase()
        .onStart {
            _uiState.value = _uiState.value.copy(isLoading = true)
        }
        .catch { e ->
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = e.message ?: "Failed to load favorites"
            )
            emit(emptyList())
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            try {
                addFavoriteUseCase(favorite)
                _uiState.value = _uiState.value.copy(successMessage = "Added to favorites")
                clearMessageAfterDelay()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            try {
                deleteFavoriteUseCase(favorite.id)
                _uiState.value = _uiState.value.copy(successMessage = "Removed from favorites")
                clearMessageAfterDelay()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    private fun clearMessageAfterDelay() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _uiState.value = _uiState.value.copy(
                successMessage = null,
                errorMessage = null
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun clearSuccess() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
}
