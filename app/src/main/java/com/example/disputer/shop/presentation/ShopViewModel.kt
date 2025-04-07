package com.example.disputer.shop.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.disputer.core.Navigation
import com.example.disputer.core.Resource
import com.example.disputer.shop.data.Shop
import com.example.disputer.shop.domain.usecase.AddShopUseCase
import com.example.disputer.shop.domain.usecase.DeleteShopUseCase
import com.example.disputer.shop.domain.utils.AddShopUiStateLiveDataWrapper
import com.example.disputer.shop.domain.utils.ImageProcessor
import com.example.disputer.training.presentation.training_coach.main.TrainingCoachMainScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopViewModel(
    private val addShopUseCase: AddShopUseCase,
    private val deleteShopUseCase: DeleteShopUseCase,
    private val navigation: Navigation,
    private val addShopUiStateLiveDataWrapper: AddShopUiStateLiveDataWrapper,
    //private val imageProcessor: ImageProcessor,
    private val viewModelScope: CoroutineScope,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {

    fun addShop(shop: Shop) {
        addShopUiStateLiveDataWrapper.update(AddShopUiState.Loading)
        viewModelScope.launch(dispatcherIo) {
            addShopUseCase.invoke(shop)
            withContext(dispatcherMain) {
                navigation.update(TrainingCoachMainScreen)
            }
        }
    }

    fun deleteShop(shop: Shop) {
        addShopUiStateLiveDataWrapper.update(AddShopUiState.Loading)
        viewModelScope.launch(dispatcherIo) {
            deleteShopUseCase.invoke(shop)
            withContext(dispatcherMain) {
                navigation.update(TrainingCoachMainScreen)
            }
        }
    }

    fun uiStateLiveData() = addShopUiStateLiveDataWrapper.liveData()
}