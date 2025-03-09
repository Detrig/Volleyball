package com.example.disputer.authentication.presentation.state

import com.example.disputer.LoginUiState
import com.example.disputer.core.LiveDataWrapper

interface LoginUiStateLiveDataWrapper : LiveDataWrapper<LoginUiState> {

    class Base : LoginUiStateLiveDataWrapper, LiveDataWrapper.Abstract<LoginUiState>()
}