package co.icristea.yuga.ui.authorization.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icristea.yuga.core.util.AuthorisationEvent
import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.use_case.SignupUser
import co.icristea.yuga.domain.authorization.use_case.ValidateEmail
import co.icristea.yuga.domain.authorization.use_case.ValidateFullName
import co.icristea.yuga.domain.authorization.use_case.ValidatePassword
import co.icristea.yuga.domain.authorization.use_case.ValidatePhone
import co.icristea.yuga.domain.authorization.use_case.ValidateTerms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUser: SignupUser,
    private val validateFullName: ValidateFullName,
    private val validateEmail: ValidateEmail,
    private val validatePhone: ValidatePhone,
    private val validatePassword: ValidatePassword,
    private val validateTerms: ValidateTerms,
) : ViewModel() {

    var state by mutableStateOf(SignupFormState())

    private val validateEventChannel = Channel<AuthorisationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    fun onEvent(event: SignupFormEvent) {
        when (event) {
            is SignupFormEvent.FullNameChanged -> {
                state = state.copy(fullName = event.fullName)
            }

            is SignupFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is SignupFormEvent.PhoneChanged -> {
                state = state.copy(phone = event.phone)
            }

            is SignupFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is SignupFormEvent.TermsChanged -> {
                state = state.copy(termsAccepted = event.isAccepted)
            }

            is SignupFormEvent.Submit -> {
                val fullNameResult = validateFullName.execute(state.fullName)
                val emailResult = validateEmail.execute(state.email)
                val phoneResult = validatePhone.execute(state.phone)
                val passwordResult = validatePassword.execute(state.password)
                val termsResult = validateTerms.execute(state.termsAccepted)

                val hasError = listOf(
                    fullNameResult,
                    emailResult,
                    phoneResult,
                    passwordResult,
                    termsResult,
                ).any { !it.successful }

                if (hasError) {
                  state = state.copy(
                        fullNameError = fullNameResult.errorMessage,
                        emailError = emailResult.errorMessage,
                        phoneError = phoneResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                        termsError = termsResult.errorMessage,
                    )
                    return

                }
                onSubmit()
            }
        }
    }

    private fun onSubmit() {
        Log.e("TAG", "onSubmit:clicked ")
        viewModelScope.launch {
            signupUser(
                state.fullName,
                state.email,
                state.phone,
                state.password
            ).onEach { result ->
                Log.e("TAG", "onSubmit: $result")
                when (result) {

                    is Response.Success -> {
                        Log.e("TAG", "onSuccessSubmit: ${result.data}")
                        validateEventChannel.send(AuthorisationEvent.Success)
                    }

                    is Response.Error -> {
                        Log.e("TAG", "onErrorSubmit: ${result.message}")
                        validateEventChannel.send(AuthorisationEvent.Error(result.message.toString()))
                    }
                }
            }.launchIn(this)
        }
    }
}