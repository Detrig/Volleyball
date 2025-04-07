package com.example.disputer.training.presentation.training_coach

import androidx.lifecycle.ViewModel
import com.example.disputer.core.Navigation
import com.example.disputer.shop.domain.repo.ShopRepository
import com.example.disputer.shop.presentation.AddShopScreen
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.AddTrainingUiStateLiveDataWrapper
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.shop.domain.utils.ShopsLiveDataWrapper
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingScreen
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingUiState
import com.example.disputer.training.presentation.training_coach.main.TrainingCoachMainScreen
import com.example.disputer.training.domain.repository.utils.TrainingsLiveDataWrapper
import com.example.disputer.training.presentation.training_parent.TrainingParentMainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingCoachViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val shopRepository: ShopRepository,
    private val trainingsLiveDataWrapper: TrainingsLiveDataWrapper,
    private val shopsLiveDataWrapper: ShopsLiveDataWrapper,
    private val addTrainingUiStateLiveDataWrapper: AddTrainingUiStateLiveDataWrapper,
    private val navigation: Navigation,
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

    fun addTraining(training: Training) {
        addTrainingUiStateLiveDataWrapper.update(AddTrainingUiState.Loading)
        viewModelScope.launch(dispatcherIO) {
            trainingsRepository.addTraining(training)
            withContext(dispatcherMain) {
                navigation.update(TrainingCoachMainScreen)
            }
        }
    }

    fun deleteTraining(training: Training) {
        addTrainingUiStateLiveDataWrapper.update(AddTrainingUiState.Loading)
        viewModelScope.launch(dispatcherIO) {
            trainingsRepository.deleteTraining(training)
            withContext(dispatcherMain) {
                navigation.update(TrainingCoachMainScreen)
            }
        }
    }

    fun addTrainingScreen() = navigation.update(AddTrainingScreen)
    fun addShopScreen() = navigation.update(AddShopScreen)

    fun addTrainingUiStateLiveData() = addTrainingUiStateLiveDataWrapper.liveData()
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