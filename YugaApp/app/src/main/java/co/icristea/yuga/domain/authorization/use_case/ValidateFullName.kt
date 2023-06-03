package co.icristea.yuga.domain.authorization.use_case

import android.util.Patterns
import co.icristea.yuga.core.util.ValidationResult

class ValidateFullName {
    fun execute(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "The name can't be blank")
        }
        if(!fullName.contains( " ")){
            return ValidationResult(
                successful = false,
                errorMessage = "introduce first name or last name"
            )
        }
        return ValidationResult(successful = true)
    }
}