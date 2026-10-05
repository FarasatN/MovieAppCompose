package com.farasatnovruzov.spendingtracker.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.farasatnovruzov.spendingtracker.core.domain.CoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : CoreRepository {

    override fun observeBalance(): Flow<Double> =
        dataStore.data.map { preferences -> preferences[KEY_BALANCE] ?: 0.0 }

    override suspend fun updateBalance(balance: Double) {
        dataStore.edit { preferences ->
            preferences[KEY_BALANCE] = balance
        }
    }

    override suspend fun getBalance(): Double = observeBalance().first()

    companion object {
        private val KEY_BALANCE = doublePreferencesKey("KEY_BALANCE")
    }
}
