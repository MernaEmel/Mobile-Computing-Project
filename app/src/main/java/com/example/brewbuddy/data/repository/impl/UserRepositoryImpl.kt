package com.example.brewbuddy.data.repository.impl

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.brewbuddy.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context): UserRepository {



    private val dataStore = context.dataStore
    private val USER_NAME = stringPreferencesKey("USER_NAME")

    override suspend fun saveUserName(name: String) {
        dataStore.edit { prefs ->
            prefs[USER_NAME] = name
        }
    }

    override fun getUserName(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[USER_NAME]
        }
    }

    override suspend fun deleteUserName() {
        dataStore.edit { prefs->
            prefs.remove( USER_NAME)
        }
    }
}