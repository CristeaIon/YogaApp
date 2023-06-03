package co.icristea.yuga.domain.authorization.use_case

import co.icristea.yuga.core.util.ValidationResult

class ValidateTerms {
    fun execute(acceptedTerms: Boolean): ValidationResult {
        if(!acceptedTerms){
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms",
            )
        }

        return ValidationResult(successful = true)
    }
}