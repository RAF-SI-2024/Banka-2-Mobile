package com.cyb.banka2_mobile.splash

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class SplashState(
    val loading: Boolean = true,
    val goToLogin: Boolean = false
)

@HiltViewModel
class SplashViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    private fun setState(reducer: SplashState.() -> SplashState) = _state.update (reducer)


    init {
        simulateSplashDelay()
    }

    private fun simulateSplashDelay() {
        viewModelScope.launch {
            delay(4000)
            setState { copy(loading = false, goToLogin = true) }
            // todo logic for navigation
        }
    }
}