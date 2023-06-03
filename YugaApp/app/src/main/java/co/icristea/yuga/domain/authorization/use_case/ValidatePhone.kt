package co.icristea.yuga.domain.authorization.use_case

import android.util.Patterns
import co.icristea.yuga.core.util.ValidationResult

class ValidatePhone {
    fun execute(phone: String): ValidationResult {
        if (phone.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "The phone can't be blank")
        }
        if(!Patterns.PHONE.matcher(phone).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid phone number"
            )
        }
        return ValidationResult(successful = true)
    }
}