package co.icristea.yuga.ui.authorization.restore.update

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icristea.yuga.core.di.AuthorizationModule_ProvideValidateRepeatedPasswordUseCaseFactory
import co.icristea.yuga.core.util.AuthorisationEvent
import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.use_case.RestoreUserPassword
import co.icristea.yuga.domain.authorization.use_case.UpdateUserPassword
import co.icristea.yuga.domain.authorization.use_case.ValidateEmail
import co.icristea.yuga.domain.authorization.use_case.ValidatePassword
import co.icristea.yuga.domain.authorization.use_case.ValidateRepeatedPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val updateUserPassword: UpdateUserPassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword,
) : ViewModel() {

    var state by mutableStateOf(UpdatePasswordFormState())

    private val validateEventChannel = Channel<AuthorisationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    fun onEvent(event: UpdatePasswordFormEvent) {
        when (event) {
            is UpdatePasswordFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is UpdatePasswordFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }

            is UpdatePasswordFormEvent.Submit -> {
                val passwordCheck =
                    validateRepeatedPassword.execute(state.password, state.repeatedPassword)

                val hasError = listOf(
                    passwordCheck,
                ).any { !it.successful }

                if (hasError) {
                    state = state.copy(
                        passwordError = passwordCheck.errorMessage,
                        repeatedPasswordError = passwordCheck.errorMessage
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
            updateUserPassword(
                state.password,
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