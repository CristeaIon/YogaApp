package co.icristea.yuga.core.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
