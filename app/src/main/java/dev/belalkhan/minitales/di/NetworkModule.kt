package dev.belalkhan.minitales.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.belalkhan.minitales.BuildConfig
import dev.belalkhan.minitales.DataStoreSessionHandler
import dev.belalkhan.minitales.UserSerializer
import dev.belalkhan.minitales.network.http.MiniTalesHttpClientBuilder
import dev.belalkhan.minitales.network.http.RequestHandler
import dev.belalkhan.minitales.storage.SessionHandler
import dev.belalkhan.minitales.storage.User
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import javax.inject.Singleton

private val Context.userDataStore: DataStore<User> by dataStore(
    fileName = "user.pb",
    serializer = UserSerializer,
)

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<User> =
        context.userDataStore

    @Provides
    fun provideSessionHandler(dataStoreSessionHandler: DataStoreSessionHandler): SessionHandler =
        dataStoreSessionHandler

    @Provides
    fun provideHttpClient(sessionHandler: SessionHandler): HttpClient =
        MiniTalesHttpClientBuilder(sessionHandler)
            .protocol(URLProtocol.HTTP)
            .host(BuildConfig.MINI_TALES_HOST)
            .port(8080)
            .build()

    @Provides
    fun provideRequestHandler(client: HttpClient) = RequestHandler(client)
}
