package co.icristea.yuga.domain.authorization.use_case

import co.icristea.yuga.core.util.ValidationResult

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The password can't be blank",
            )
        }

        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 6 characters",
            )
        }

        val containsLetterAndDigits = password.any{it.isLetterOrDigit()}
        if (!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain at least one letter and digit"
            )
        }
        return ValidationResult(successful = true)
    }
}