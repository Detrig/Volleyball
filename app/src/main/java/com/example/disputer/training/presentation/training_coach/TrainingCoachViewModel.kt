package com.example.disputer.training.presentation.training_coach

import androidx.lifecycle.ViewModel
import com.example.disputer.core.Navigation
import com.example.disputer.shop.presentation.AddShopScreen
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.AddTrainingUiStateLiveDataWrapper
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingScreen
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingUiState
import com.example.disputer.training.presentation.training_coach.main.TrainingCoachMainScreen
import com.example.disputer.training.presentation.training_parent.TrainingsLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingCoachViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val trainingsLiveDataWrapper: TrainingsLiveDataWrapper,
    private val addTrainingUiStateLiveDataWrapper: AddTrainingUiStateLiveDataWrapper,
    private val navigation: Navigation,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    init {
        //observeTrainings()
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
    fun trainingsLiveData() = trainingsLiveDataWrapper.liveData()

    fun addTrainingUiStateLiveData() = addTrainingUiStateLiveDataWrapper.liveData()
//    private fun observeTrainings() {
//        _trainingsLiveData.addSource(trainingsRepository.observeTrainingsLiveData()) { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    trainingsLiveDataWrapper.update(TrainingsUiState.Success(resource.data))
//                    _trainingsLiveData.value = TrainingsUiState.Success(resource.data)
//                }
//                is Resource.Error -> {
//                    trainingsLiveDataWrapper.update(TrainingsUiState.Error(resource.message))
//                    _trainingsLiveData.value = TrainingsUiState.Error(resource.message)
//                }
//                is Resource.Loading -> {
//                    trainingsLiveDataWrapper.update(TrainingsUiState.Loading)
//                    _trainingsLiveData.value = TrainingsUiState.Loading
//                }
//            }
//        }
//    }
}