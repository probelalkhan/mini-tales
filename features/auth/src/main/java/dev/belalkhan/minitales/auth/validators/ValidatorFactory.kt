package dev.belalkhan.minitales.auth.validators

import dev.belalkhan.minitales.common.utils.validator.InputValidator
import javax.inject.Inject

class ValidatorFactory @Inject constructor() {

    private val validators: Map<AuthParams, InputValidator> = mapOf(
        AuthParams.FULL_NAME to FullNameValidator(),
        AuthParams.EMAIL to EmailValidator(),
        AuthParams.PASSWORD to PasswordValidator(),
    )

    fun get(param: AuthParams): InputValidator {
        return validators.getOrElse(param) {
            throw IllegalArgumentException("Validator not found; make sure you have provided correct param")
        }
    }
}
