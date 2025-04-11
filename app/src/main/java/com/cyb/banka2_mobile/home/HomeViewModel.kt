package com.cyb.banka2_mobile.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.banka2_mobile.home.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state = _state.asStateFlow()

    private fun setState(reducer: HomeContract.HomeState.() -> HomeContract.HomeState) = _state.update (reducer)

    private val events = MutableSharedFlow<HomeContract.HomeEvent>()
    fun setEvent(event: HomeContract.HomeEvent) = viewModelScope.launch { events.emit(event) }


    init {
        populateState()
    }

    private fun populateState() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            val homeUiModel = homeRepository.getUserInfo()
            setState { copy(homeUiModel = homeUiModel) }
        }
    }
}