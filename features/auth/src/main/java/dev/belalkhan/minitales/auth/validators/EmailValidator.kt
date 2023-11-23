package dev.belalkhan.minitales.auth.validators

import dev.belalkhan.minitales.auth.R
import dev.belalkhan.minitales.common.utils.validator.InputValidator
import dev.belalkhan.minitales.common.utils.validator.ValidationResult

class EmailValidator : InputValidator {

    private val emailPattern = Regex(
        "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,}$",
        RegexOption.IGNORE_CASE,
    )

    override fun validate(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult(R.string.error_email_empty)
        } else if (!emailPattern.matches(input)) {
            return ValidationResult(R.string.error_email_invalid)
        } else {
            ValidationResult()
        }
    }
}
