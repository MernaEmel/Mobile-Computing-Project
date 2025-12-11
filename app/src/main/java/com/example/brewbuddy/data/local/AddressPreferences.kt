package com.example.brewbuddy.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "address_preferences"
        private const val KEY_STREET_ADDRESS = "street_address"
        private const val KEY_CITY = "city"
        private const val KEY_POSTAL_CODE = "postal_code"
        private const val KEY_NOTES = "notes"
        private const val KEY_HAS_ADDRESS = "has_address"
    }

    fun saveAddress(
        streetAddress: String,
        city: String,
        notes: String = ""
    ) {
        sharedPreferences.edit().apply {
            putString(KEY_STREET_ADDRESS, streetAddress)
            putString(KEY_CITY, city)
            putString(KEY_NOTES, notes)
            putBoolean(KEY_HAS_ADDRESS, true)
            apply()
        }
    }

    fun getAddress(): Address? {
        return if (hasAddress()) {
            Address(
                streetAddress = sharedPreferences.getString(KEY_STREET_ADDRESS, "") ?: "",
                city = sharedPreferences.getString(KEY_CITY, "") ?: "",
                postalCode = "",
                notes = sharedPreferences.getString(KEY_NOTES, "") ?: ""
            )
        } else {
            null
        }
    }

    fun hasAddress(): Boolean {
        return sharedPreferences.getBoolean(KEY_HAS_ADDRESS, false)
    }

    fun clearAddress() {
        sharedPreferences.edit().apply {
            remove(KEY_STREET_ADDRESS)
            remove(KEY_CITY)
            remove(KEY_POSTAL_CODE)
            remove(KEY_NOTES)
            putBoolean(KEY_HAS_ADDRESS, false)
            apply()
        }
    }

    fun getFormattedAddress(): String {
        val address = getAddress()
        return if (address != null) {
            buildString {
                append(address.streetAddress)
                if (address.city.isNotEmpty()) {
                    append(", ${address.city}")
                }
            }
        } else {
            "No saved address"
        }
    }
}

data class Address(
    val streetAddress: String,
    val city: String,
    val postalCode: String,
    val notes: String
)
