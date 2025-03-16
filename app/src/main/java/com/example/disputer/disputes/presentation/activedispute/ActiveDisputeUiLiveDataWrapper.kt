package com.example.disputer.disputes.presentation.activedispute

import com.example.disputer.core.LiveDataWrapper

interface ActiveDisputeUiLiveDataWrapper : LiveDataWrapper.Mutable<ActiveDisputeUiState> {

    class Base : ActiveDisputeUiLiveDataWrapper, LiveDataWrapper.Abstract<ActiveDisputeUiState>()
}