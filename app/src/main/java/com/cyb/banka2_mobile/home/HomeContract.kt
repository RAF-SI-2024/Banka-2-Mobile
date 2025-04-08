package com.cyb.banka2_mobile.home

import androidx.compose.runtime.Immutable
import com.cyb.banka2_mobile.home.models.HomeUiModel

interface HomeContract {
    @Immutable
    data class HomeState(
        val loading: Boolean = false,
        val homeUiModel: HomeUiModel? = null
    )

    sealed class HomeEvent{
        data object TestEvent: HomeEvent()
    }
}