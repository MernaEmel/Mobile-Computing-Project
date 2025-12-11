package com.example.brewbuddy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.data.local.database.entities.OrderHistory
import com.example.brewbuddy.domain.repository.OrderHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val repository: OrderHistoryRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderHistory>>(emptyList())
    val orders: StateFlow<List<OrderHistory>> = _orders.asStateFlow()

    private val _isRecentView = MutableStateFlow(true)
    val isRecentView: StateFlow<Boolean> = _isRecentView.asStateFlow()

    init {
        loadRecentOrders()
    }

    fun addOrder(order: OrderHistory) {
        viewModelScope.launch {
            repository.addOrder(order)
            reloadCurrentView()
        }
    }

    fun loadRecentOrders() {
        viewModelScope.launch {
            _isRecentView.value = true
            _orders.value = repository.getRecentOrders()
        }
    }

    fun loadPastOrders() {
        viewModelScope.launch {
            _isRecentView.value = false
            _orders.value = repository.getPastOrders()
        }
    }

    fun toggleView() {
        if (_isRecentView.value) {
            loadPastOrders()
        } else {
            loadRecentOrders()
        }
    }

    private fun reloadCurrentView() {
        viewModelScope.launch {
            if (_isRecentView.value) {
                _orders.value = repository.getRecentOrders()
            } else {
                _orders.value = repository.getPastOrders()
            }
        }
    }
}