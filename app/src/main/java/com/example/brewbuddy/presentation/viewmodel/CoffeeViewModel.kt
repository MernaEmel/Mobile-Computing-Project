package com.example.brewbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.data.local.database.entities.CoffeeEntity
import com.example.brewbuddy.domain.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeViewModel @Inject constructor(
    private val repository: CoffeeRepository
) : ViewModel() {

    private val _coffeeList = MutableStateFlow<List<CoffeeEntity>>(emptyList())
    val coffeeList: StateFlow<List<CoffeeEntity>> = _coffeeList.asStateFlow()

    private var currentCategory: String? = null
    private var currentSearch: String = ""

    init {
        viewModelScope.launch {
            repository.fetchHotCoffees()
            repository.fetchColdCoffees()
        }

        viewModelScope.launch {
            repository.getCachedCoffees().collect { list ->
                _coffeeList.value = applyFilters(list)
            }
        }
    }

    fun filter(query: String) {
        currentSearch = query
        updateFilteredList()
    }

    fun filterByCategory(category: String) {
        currentCategory = category
        updateFilteredList()
    }

    private fun updateFilteredList() {
        viewModelScope.launch {
            repository.getCachedCoffees().collect { list ->
                _coffeeList.value = applyFilters(list)
            }
        }
    }

    private fun applyFilters(list: List<CoffeeEntity>): List<CoffeeEntity> {
        return list.filter { coffee ->
            (currentCategory == null || coffee.category == currentCategory) &&
                    (currentSearch.isBlank() || coffee.title.contains(currentSearch, ignoreCase = true))
        }
    }
}
