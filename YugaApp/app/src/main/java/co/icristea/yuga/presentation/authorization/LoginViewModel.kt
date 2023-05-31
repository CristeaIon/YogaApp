package co.icristea.yuga.presentation.authorization

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icristea.yuga.core.util.Response
import co.icristea.yuga.domain.authorization.use_case.LoginUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUser: LoginUser
) : ViewModel() {
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onSubmit() {
        Log.e("TAG", "onSubmit:clicked ", )
        viewModelScope.launch {
            loginUser(
                _email.value,
                _password.value
            ).onEach { result ->
                Log.e("TAG", "onSubmit: $result", )
                when (result) {
                    is Response.Loading -> {
                        Log.e("TAG", "onLoadingSubmit: ${result.data}")
                    }

                    is Response.Success -> {
                        Log.e("TAG", "onSuccessSubmit: ${result.data}")
                    }

                    is Response.Error -> {
                        Log.e("TAG", "onErrorSubmit: ${result.message}")
                    }
                }
            }.launchIn(this)
        }
    }
}