package com.tec9ers.thunderstorm.utils

import android.content.Context
import android.text.TextUtils
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUtils @Inject constructor(@ApplicationContext val context: Context) {
    //TO read cities
    val citiesListLiveData: LiveData<List<String>>
        get() = citiesFlow.map { s -> s.split(" ") }.asLiveData()

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "cities_store"
    )

    private val cities = preferencesKey<String>("cities")

    private val citiesFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[cities] ?: " "
        }

    //To ADD City
    suspend fun addCitiesDataStore(city: String) {
        if (TextUtils.isEmpty(city)) throw IOException("Parameter city cannot be empty")
        dataStore.edit { preferences ->
            val existingCities = preferences[cities]
            preferences[cities] = "$existingCities $city"
        }
    }

    //To change list of cities
    suspend fun setCitiesDataStore(city: List<String>) {
        dataStore.edit { preferences ->
            preferences[cities] = (city.joinToString(" "))
        }

    }
}