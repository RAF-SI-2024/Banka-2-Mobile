package com.cyb.banka2_mobile.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.banka2_mobile.login.LoginContract.LoginEvent.DoLogin
import com.cyb.banka2_mobile.login.LoginContract.LoginEvent.EmailFieldChange
import com.cyb.banka2_mobile.login.LoginContract.LoginEvent.PasswordFieldChange
import com.cyb.banka2_mobile.login.LoginContract.LoginEvent.SwitchPasswordVisibility
import com.cyb.banka2_mobile.login.api.model.LoginRequest
import com.cyb.banka2_mobile.login.repository.LoginRepository
import com.cyb.banka2_mobile.networking.di.TokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenProvider: TokenProvider
): ViewModel() {
    private val _state = MutableStateFlow(LoginContract.LoginState())
    val state = _state.asStateFlow()

    private fun setState(reducer: LoginContract.LoginState.() -> LoginContract.LoginState) = _state.update (reducer)

    private val events = MutableSharedFlow<LoginContract.LoginEvent>()
    fun setEvent(event: LoginContract.LoginEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    DoLogin -> {
                        val isLoggedIn = loginRepository.doLogin(
                            LoginRequest(
                                email = state.value.emailValue, password = state.value.passwordValue
                            )
                        )

                        if (isLoggedIn) {
                            val token = loginRepository.getUser().token
                            tokenProvider.setToken(token)
                            setState { copy(navigateToHome = true) }
                        } else {
                            setState { copy(raiseError = true) }
                        }
                    }
                    is EmailFieldChange -> {
                        viewModelScope.launch {
                            setState { copy(emailValue = event.value, raiseError = false) }
                        }
                    }
                    is PasswordFieldChange -> {
                        viewModelScope.launch {
                            setState { copy(passwordValue = event.value, raiseError = false) }
                        }
                    }
                    SwitchPasswordVisibility -> {
                        viewModelScope.launch {
                            setState { copy(passwordFieldVisible = !state.value.passwordFieldVisible) }
                        }
                    }
                }
            }
        }
    }
}