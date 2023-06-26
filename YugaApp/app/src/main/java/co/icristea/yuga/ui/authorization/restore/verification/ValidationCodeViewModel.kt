package co.icristea.yuga.ui.authorization.restore.verification

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icristea.yuga.core.util.AuthorisationEvent
import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.use_case.ValidateCode
import co.icristea.yuga.domain.storage.IStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ValidationCodeViewModel @Inject constructor(
    private val validateCode: ValidateCode,
    private val store: IStorage,
) : ViewModel() {

    var state by mutableStateOf(ValidationFormState())

    private val validateEventChannel = Channel<AuthorisationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    fun onEvent(event: ValidationFormEvent) {
        when (event) {

            is ValidationFormEvent.CodeChanged -> {
                state = state.copy(code = event.code)
            }

            is ValidationFormEvent.SubmitCode -> {
                onSubmit()
            }
        }

    }


    private fun onSubmit() {
        Log.e("TAG", "onSubmit:clicked ")
        viewModelScope.launch {
            store.getRestorePasswordResponse().collect {
                validateCode(
                    it.id, it.contact,
                    state.code,
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
}