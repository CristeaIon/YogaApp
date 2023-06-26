package co.icristea.yuga.domain.storage

import co.icristea.yuga.data.local.RestoreDao
import kotlinx.coroutines.flow.Flow

interface IStorage {
    suspend fun saveRestorePasswordResponse(id: String, contact: String)
    suspend fun getRestorePasswordResponse(): Flow<RestoreDao>
    suspend fun saveOnboardingIsSkipped(isSkipped: Boolean)
    suspend fun getOnboardingIsSkipped(): Flow<Boolean>
}