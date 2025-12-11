package com.example.brewbuddy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.usecase.home.GetBestSellerCoffeeUseCase
import com.example.brewbuddy.domain.usecase.GetCoffeesByCategoryUseCase
import com.example.brewbuddy.domain.usecase.home.GetWeekRecommendationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBestSellerCoffeeUseCase: GetBestSellerCoffeeUseCase,
    private val getWeekRecommendationUseCase: GetWeekRecommendationUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private val _isLoadingBestSeller = MutableLiveData(false)
    val isLoadingBestSeller: LiveData<Boolean> get() = _isLoadingBestSeller
    private val _isLoadingRecommendations = MutableLiveData(false)
    val isLoadingRecommendations: LiveData<Boolean> get() = _isLoadingRecommendations

    init {
        loadBestSeller()
        getRecommendations()
    }

    fun loadBestSeller() {
        viewModelScope.launch {
            _isLoadingBestSeller.value = true
            _uiState.value = _uiState.value.copy(bestSeller = getBestSellerCoffeeUseCase.invoke())
            _isLoadingBestSeller.value = false
        }
    }

    fun getRecommendations() {
        viewModelScope.launch {
            _isLoadingRecommendations.value = true
            _uiState.value =
                _uiState.value.copy(recommendations = getWeekRecommendationUseCase.invoke())
            _isLoadingRecommendations.value = false
        }
    }
}

data class HomeUiState(
    var bestSeller: Coffee? = null,
    val recommendations: List<Coffee> = emptyList(),
    val userName: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
