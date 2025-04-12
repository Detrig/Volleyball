package com.example.disputer.children.domain.utils

import com.example.disputer.children.presentation.add.AddChildrenUiState
import com.example.disputer.core.LiveDataWrapper

interface AddChildrenUiStateLiveDataWrapper : LiveDataWrapper.Mutable<AddChildrenUiState> {

    class Base : AddChildrenUiStateLiveDataWrapper, LiveDataWrapper.Abstract<AddChildrenUiState>()
}