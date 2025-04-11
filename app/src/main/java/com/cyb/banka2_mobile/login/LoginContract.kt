package com.cyb.banka2_mobile.login

import androidx.compose.runtime.Immutable

interface LoginContract {
    @Immutable
    data class LoginState(
        val emailValue: String = "",
        val passwordValue: String = "",
        val emailFieldEmpty: Boolean = false,
        val passwordFieldEmpty: Boolean = false,
        val passwordFieldVisible: Boolean = false,
        val raiseError: Boolean = false,
        val navigateToHome: Boolean = false
    )

    sealed class LoginEvent {
        data object DoLogin: LoginEvent()
        data object SwitchPasswordVisibility: LoginEvent()
        data class EmailFieldChange(val value: String): LoginEvent()
        data class PasswordFieldChange(val value: String): LoginEvent()
    }
}