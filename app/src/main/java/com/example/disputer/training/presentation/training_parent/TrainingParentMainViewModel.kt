package com.example.disputer.training.presentation.training_parent

import androidx.lifecycle.ViewModel
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.TrainingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingParentMainViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val trainingsLiveDataWrapper: TrainingsLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    init {
        viewModelScope.launch(dispatcherIO) {
            val trainingsList = trainingsRepository.getAllTrainings()
            withContext(dispatcherMain) {
                if (trainingsList.data != null)
                trainingsLiveDataWrapper.update(trainingsList.data)
            }
        }

    }
    fun trainingsLiveData() = trainingsLiveDataWrapper.liveData()

    //fun getAllTrainings() : List<Training> = trainingsRepository.getAllTrainings()
}
