package dev.belalkhan.minitales.storage

import kotlinx.coroutines.flow.Flow

interface SessionHandler {
    suspend fun setCurrentUser(id: Int, authKey: String?)
    fun getCurrentUser(): Flow<CurrentUser>
    suspend fun clear()
}
