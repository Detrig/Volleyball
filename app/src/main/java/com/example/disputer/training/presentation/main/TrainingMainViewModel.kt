package com.example.disputer.training.presentation.main

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.Training
import com.example.disputer.training.data.TrainingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TrainingMainViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val trainingsLiveDataWrapper: TrainingsLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    init {
        val trainingsList = trainingsRepository.getAllTrainings()
        trainingsLiveDataWrapper.update(trainingsList)
    }
    fun trainingsLiveData() = trainingsLiveDataWrapper.liveData()

    fun getAllTrainings() : List<Training> = trainingsRepository.getAllTrainings()
}
