package dev.belalkhan.minitales

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.belalkhan.minitales.storage.CurrentUser
import dev.belalkhan.minitales.storage.SessionHandler
import dev.belalkhan.minitales.storage.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.userDataStore: DataStore<User> by dataStore(
    fileName = "user.pb",
    serializer = UserSerializer,
)

class DataStoreSessionHandler @Inject constructor(
    @ApplicationContext private val context: Context,
) : SessionHandler {

    override suspend fun setCurrentUser(id: Int, authKey: String?) {
        context.userDataStore.updateData {
            it.toBuilder()
                .setAuthKey(authKey)
                .setId(id)
                .build()
        }
    }

    override fun getCurrentUser(): Flow<CurrentUser> {
        return context.userDataStore.data.map {
            CurrentUser(it.id, it.authKey)
        }
    }

    override suspend fun clear() {
        context.userDataStore.updateData {
            it.toBuilder()
                .setAuthKey("")
                .setId(-1)
                .build()
        }
    }
}
