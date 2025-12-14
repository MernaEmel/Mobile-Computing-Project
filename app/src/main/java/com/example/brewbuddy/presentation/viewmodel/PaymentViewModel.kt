package com.example.brewbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.data.local.AddressPreferences
import com.example.brewbuddy.data.local.database.entities.OrderHistory
import com.example.brewbuddy.data.repository.impl.OrderHistoryRepositoryImp
import com.example.brewbuddy.presentation.viewmodels.OrderHistoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val addressPreferences: AddressPreferences,
    private val orderHistoryRepositoryImp: OrderHistoryRepositoryImp
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()


    fun setPaymentData(
        coffeeId: Int,
        coffeeName: String,
        coffeePrice: Double,
        quantity: Int,
        totalAmount: Double,
        imageUrl: String
    ) {
        android.util.Log.d("PaymentViewModel", "Setting payment data - ID: $coffeeId, Name: $coffeeName, Price: $coffeePrice, Quantity: $quantity")

        val deliveryFee = 25.0
        val packagingFee = 15.0
        val promoDiscount = 0.0

        val finalTotal = (coffeePrice * quantity) + deliveryFee + packagingFee - promoDiscount

        _uiState.value = _uiState.value.copy(
            coffeeId = coffeeId,
            coffeeName = coffeeName,
            coffeePrice = coffeePrice,
            quantity = quantity,
            deliveryFee = deliveryFee,
            packagingFee = packagingFee,
            promoDiscount = promoDiscount,
            totalAmount = finalTotal,
            imageUrl = imageUrl,
        )

        android.util.Log.d("PaymentViewModel", "Updated UI state - Name: ${_uiState.value.coffeeName}, Price: ${_uiState.value.coffeePrice}, Total: ${_uiState.value.totalAmount}")

        loadAddress()
    }

    fun placeOrder() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true
                )
                val orderHistory = createOrderHistoryFromCurrentState()

                orderHistoryRepositoryImp.addOrder(orderHistory)

                kotlinx.coroutines.delay(1000)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    orderPlaced = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }


    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun loadAddress() {
        val savedAddress = addressPreferences.getFormattedAddress()
        _uiState.value = _uiState.value.copy(
            deliveryAddress = savedAddress
        )
    }

    fun saveAddress(streetAddress: String, city: String,  notes: String) {
        addressPreferences.saveAddress(streetAddress, city, notes)
        loadAddress()
    }

    fun hasAddress(): Boolean {
        return addressPreferences.hasAddress()
    }
    private fun createOrderHistoryFromCurrentState(): OrderHistory {
        val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        return OrderHistory(
            coffeeId = _uiState.value.coffeeId,
            title = _uiState.value.coffeeName,
            image = _uiState.value.imageUrl,
            date = currentDate,
            quantity = _uiState.value.quantity,
            totalPrice = _uiState.value.totalAmount,
            promo = _uiState.value.promoDiscount,
            deliveryFee = _uiState.value.deliveryFee,
            packagingFee = _uiState.value.packagingFee,
            address = _uiState.value.deliveryAddress
        )
    }
}

data class PaymentUiState(
    val coffeeId: Int = 0,
    val coffeeName: String = "",
    val coffeePrice: Double = 0.0,
    val quantity: Int = 1,
    val deliveryFee: Double = 25.0,
    val packagingFee: Double = 10.0,
    val promoDiscount: Double = 0.0,
    val totalAmount: Double = 0.0,
    val deliveryAddress: String = "No saved address",
    val isLoading: Boolean = false,
    val orderPlaced: Boolean = false,
    val error: String? = null,
    val imageUrl:String=""
)
