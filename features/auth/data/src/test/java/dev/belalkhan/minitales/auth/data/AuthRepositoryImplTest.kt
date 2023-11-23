package dev.belalkhan.minitales.auth.data

import com.google.common.truth.Truth.assertWithMessage
import dev.belalkhan.minitales.network.NetworkResult
import dev.belalkhan.minitales.network.http.RequestHandler
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

const val C_EMAIL = "probelalkhan@gmail.com"
const val C_PASSWORD = "123456"

@RunWith(JUnit4::class)
class AuthRepositoryImplTest {

    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun initRepository() {
        val httpClient = ApiMockEngine()
        val requestHandler = RequestHandler(httpClient.client)
        repository = AuthRepositoryImpl(requestHandler)
    }

    @Test
    fun `login should succeed with valid credentials`(): Unit = runBlocking {
        // Given
        val validUserCredentials = UserLoginRequest("probelalkhan@gmail.com", "123456")

        // When
        val result = repository.login(validUserCredentials)

        // Then
        assertWithMessage("Expected successful login result, but was ${result::class.simpleName}")
            .that(result)
            .instanceOf(NetworkResult.Success::class)

        val userData = (result as NetworkResult.Success).result.data
        with(userData) {
            assertWithMessage("Auth token should not be null").that(authToken).isNotNull()
            assertWithMessage("User id should be 1").that(id).isEqualTo(1)
            assertWithMessage("Avatar should be empty").that(avatar).isEmpty()
            assertWithMessage("Email should not be empty").that(email).isNotEmpty()
            assertWithMessage("Created at should not be empty").that(createdAt).isNotEmpty()
            assertWithMessage("Full name should not be empty").that(fullName).isNotEmpty()
        }
    }

    @Test
    fun `login should fail with invalid credentials`(): Unit = runBlocking {
        // Given
        val validUserCredentials = UserLoginRequest("belal@gmail.com", "123456")

        // When
        val result = repository.login(validUserCredentials)

        // Then
        assertWithMessage("Expected error result, but was ${result::class.simpleName}")
            .that(result)
            .instanceOf(NetworkResult.Error::class)
    }
}
