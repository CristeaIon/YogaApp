package co.icristea.yuga.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import co.icristea.yuga.domain.storage.IStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Storage(
    private val dataStore: DataStore<Preferences>,
) : IStorage {
    private object PreferencesKeys {
        val ID = stringPreferencesKey("id")
        val CONTACT = stringPreferencesKey("contact")
        val IsSkipped = booleanPreferencesKey("isSkipped")
    }

    override suspend fun saveRestorePasswordResponse(id: String, contact: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ID] = id
            preferences[PreferencesKeys.CONTACT] = contact
        }
    }

    override suspend fun getRestorePasswordResponse(): Flow<RestoreDao> {
        return dataStore.data.map {
            RestoreDao(
                id = it[PreferencesKeys.ID] ?: "",
                contact = it[PreferencesKeys.CONTACT] ?: ""
            )
        }
    }

    override suspend fun saveOnboardingIsSkipped(isSkipped: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IsSkipped] = isSkipped
        }
    }

    override suspend fun getOnboardingIsSkipped(): Flow<Boolean> {
        return dataStore.data.map {
            it[PreferencesKeys.IsSkipped] ?: false
        }
    }
}

