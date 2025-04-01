package com.example.disputer.training.presentation.schedule

import androidx.lifecycle.ViewModel
import com.example.disputer.training.data.TrainingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ScheduleViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun getAllTrainings() = trainingsRepository.getAllTrainings()
}