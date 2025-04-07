package com.example.disputer.training.presentation.training_parent

import androidx.lifecycle.ViewModel
import com.example.disputer.core.Navigation
import com.example.disputer.shop.domain.repo.ShopRepository
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.shop.domain.utils.ShopsLiveDataWrapper
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.utils.ClickedTrainingLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.TrainingsLiveDataWrapper
import com.example.disputer.training.presentation.training_sign_up.TrainingSignUpScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class TrainingParentMainViewModel(
    private val navigation: Navigation,
    private val trainingsRepository: TrainingsRepository,
    private val shopRepository : ShopRepository,
    private val trainingsLiveDataWrapper: TrainingsLiveDataWrapper,
    private val shopsLiveDataWrapper: ShopsLiveDataWrapper,
    private val clickedTrainingLiveDataWrapper: ClickedTrainingLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    init {
        viewModelScope.launch(dispatcherIO) {
            val trainingsList = trainingsRepository.getAllTrainings()
            val shopsList = shopRepository.getShops()
            withContext(dispatcherMain) {
                if (trainingsList.data != null)
                    trainingsLiveDataWrapper.update(trainingsList.data)

                if (shopsList.data != null)
                    shopsLiveDataWrapper.update(shopsList.data)
            }
        }

        observeTrainingsAndShops()
    }

    fun trainingDetailsScreen(training: Training) {
        clickedTrainingLiveDataWrapper.update(training)
        navigation.update(TrainingSignUpScreen)
    }

    fun trainingsLiveData() = trainingsLiveDataWrapper.liveData()
    fun shopsLiveData() = shopsLiveDataWrapper.liveData()

    fun observeTrainingsAndShops() {
        trainingsRepository.observeTrainingsLiveData().observeForever {
            it.data?.let {
                trainingsLiveDataWrapper.update(it)
            }
        }

        shopRepository.observeShopsLiveData().observeForever {
            it.data?.let {
                shopsLiveDataWrapper.update(it)
            }
        }
    }
}
