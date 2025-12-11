package com.example.brewbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun setCoffee(coffee: Coffee) {
        _uiState.value = _uiState.value.copy(coffee = coffee)
        checkIfFavorite(coffee.id)
    }
    
    private fun checkIfFavorite(coffeeId: Int) {
        viewModelScope.launch {
            val isFavorite = favoritesRepository.isFavorite(coffeeId)
            _uiState.value = _uiState.value.copy(isFavorite = isFavorite)
        }
    }
    
    fun toggleFavorite() {
        val coffee = _uiState.value.coffee
        val currentFavoriteState = _uiState.value.isFavorite
        
        if (coffee != null) {
            viewModelScope.launch {
                try {
                    if (currentFavoriteState) {
                        favoritesRepository.removeFavorite(coffee.id)
                        _uiState.value = _uiState.value.copy(isFavorite = false)
                    } else {
                        val favorite = Favorite(
                            id = coffee.id,
                            name = coffee.title,
                            image = coffee.image,
                            priceCents = coffee.price,
                            isHot = coffee.category.name == "HOT"
                        )
                        favoritesRepository.addFavorite(favorite)
                        _uiState.value = _uiState.value.copy(isFavorite = true)
                    }
                } catch (e: Exception) {
                    // Handle error if needed
                    e.printStackTrace()
                }
            }
        }
    }

    fun incrementQuantity() {
        val currentQuantity = _uiState.value.quantity
        _uiState.value = _uiState.value.copy(quantity = currentQuantity + 1)
    }

    fun decrementQuantity() {
        val currentQuantity = _uiState.value.quantity
        if (currentQuantity > 1) {
            _uiState.value = _uiState.value.copy(quantity = currentQuantity - 1)
        }
    }

    fun proceedToPayment() {
        val coffee = _uiState.value.coffee
        val quantity = _uiState.value.quantity
        
        if (coffee != null) {
            _uiState.value = _uiState.value.copy(
                shouldNavigateToPayment = true,
                selectedCoffee = coffee,
                finalQuantity = quantity
            )
        }
    }

    fun onNavigatedToPayment() {
        _uiState.value = _uiState.value.copy(shouldNavigateToPayment = false)
    }
}

data class DetailsUiState(
    val coffee: Coffee? = null,
    val quantity: Int = 1,
    val shouldNavigateToPayment: Boolean = false,
    val selectedCoffee: Coffee? = null,
    val finalQuantity: Int = 1,
    val isFavorite: Boolean = false
)
