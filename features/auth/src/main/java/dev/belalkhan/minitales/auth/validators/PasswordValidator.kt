package dev.belalkhan.minitales.auth.validators

import dev.belalkhan.minitales.auth.R
import dev.belalkhan.minitales.common.utils.validator.InputValidator
import dev.belalkhan.minitales.common.utils.validator.ValidationResult

class PasswordValidator : InputValidator {

    override fun validate(input: String): ValidationResult {
        return if (input.length < 6) {
            ValidationResult(R.string.error_password)
        } else {
            ValidationResult()
        }
    }
}
