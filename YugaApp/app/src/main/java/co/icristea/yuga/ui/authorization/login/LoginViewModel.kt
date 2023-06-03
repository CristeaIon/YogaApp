package co.icristea.yuga.ui.authorization.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icristea.yuga.core.util.AuthorisationEvent
import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.use_case.LoginUser
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
class LoginViewModel @Inject constructor(
    private val loginUser: LoginUser,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val validateEventChannel = Channel<AuthorisationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is LoginFormEvent.Submit -> {
                val emailResult = validateEmail.execute(state.email)
                val passwordResult = validatePassword.execute(state.password)

                val hasError = listOf(emailResult, passwordResult).any { !it.successful }
                if (hasError) {
                    state = state.copy(
                        emailError = emailResult.errorMessage,
                        passwordError = passwordResult.errorMessage
                    )
                    return
                }
                onSubmit()
            }
        }
    }


    private fun onSubmit() {
        viewModelScope.launch {
            loginUser(
                state.email,
                state.password,
            ).onEach { result ->
                when (result) {

                    is Response.Success -> {
                        if (result.data != null) {
                            validateEventChannel.send(AuthorisationEvent.Success)
                        }
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