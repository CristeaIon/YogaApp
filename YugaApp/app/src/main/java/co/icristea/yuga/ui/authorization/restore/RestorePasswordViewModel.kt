package co.icristea.yuga.ui.authorization.restore

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icristea.yuga.core.util.AuthorisationEvent
import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.use_case.RestoreUserPassword
import co.icristea.yuga.domain.authorization.use_case.ValidateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RestorePasswordViewModel @Inject constructor(
    private val restoreUserPassword: RestoreUserPassword,
    private val validateEmail: ValidateEmail,
) : ViewModel() {

    var state by mutableStateOf(RestoreFormState())

    private val validateEventChannel = Channel<AuthorisationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    fun onEvent(event: RestoreFormEvent) {
        when (event) {

            is RestoreFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is RestoreFormEvent.Submit -> {
                val emailResult = validateEmail.execute(state.email)

                val hasError = listOf(
                    emailResult,
                ).any { !it.successful }

                if (hasError) {
                  state = state.copy(
                        emailError = emailResult.errorMessage,
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
            restoreUserPassword(
                state.email,
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