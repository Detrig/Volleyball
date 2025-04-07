package com.example.disputer.shop.domain.utils

import com.example.disputer.core.LiveDataWrapper
import com.example.disputer.shop.data.Shop
import com.example.disputer.shop.presentation.AddShopUiState
import com.example.disputer.training.data.Training

interface AddShopUiStateLiveDataWrapper : LiveDataWrapper.Mutable<AddShopUiState> {

    class Base : AddShopUiStateLiveDataWrapper, LiveDataWrapper.Abstract<AddShopUiState>()
}