package dev.belalkhan.minitales.common.domain.usecases

import dev.belalkhan.minitales.storage.SessionHandler
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionHandler: SessionHandler,
) {
    suspend fun invoke() {
        sessionHandler.clear()
    }
}
