package com.maxi.newscleanarchitecture.common

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.maxi.newscleanarchitecture.common.Constants.FIRST_FETCH_DONE
import com.maxi.newscleanarchitecture.common.Constants.FIRST_LAUNCH_PREF
import kotlinx.coroutines.flow.first

class FirstLaunchManager(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(name = FIRST_LAUNCH_PREF)
    private val firstFetchDone = booleanPreferencesKey(FIRST_FETCH_DONE)

    suspend fun checkFirstFetchDone(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[firstFetchDone] ?: false
    }

    suspend fun markFirstFetchDone() {
        context.dataStore.edit { prefs ->
            prefs[firstFetchDone] = true
        }
    }
}