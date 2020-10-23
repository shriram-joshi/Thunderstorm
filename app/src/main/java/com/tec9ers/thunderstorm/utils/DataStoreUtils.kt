package com.tec9ers.thunderstorm.utils

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUtils @Inject constructor(@ApplicationContext val context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "cities_store"
    )

    private val cities = preferencesKey<String>("cities")

    // To Read cities
    val citiesFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[cities] ?: " "
        }

    //To ADD City
    suspend fun addCities(city: String) {
        dataStore.edit { preferences ->
            val citiesValue = preferences[cities] ?: " "
            preferences[cities] = (citiesValue + city)
        }

    }
}