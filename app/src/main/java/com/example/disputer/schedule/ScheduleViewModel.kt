package com.example.disputer.schedule

import androidx.lifecycle.ViewModel
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.TrainingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun getAllTrainings() : List<Training> = listOf()
}