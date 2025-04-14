package com.example.disputer.schedule.presentation

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.domain.usecases.GetChildrenByIdUseCase
import com.example.disputer.children.domain.usecases.GetChildrenTrainings
import com.example.disputer.parent.data.Parent
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.training.domain.repository.utils.FutureTrainingListLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.TrainingsLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val trainingsLiveDataWrapper: TrainingsLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        getTrainings()
        observeTrainingsAndShops()
    }

    fun getTrainings() {
        viewModelScope.launch(dispatcherIO) {
            val trainings = trainingsRepository.getAllTrainings().data

            withContext(dispatcherMain) {
                trainings?.let {
                    trainingsLiveDataWrapper.update(trainings)
                }
            }
        }
    }

    fun observeTrainingsAndShops() {
        trainingsRepository.observeTrainingsLiveData().observeForever {
            it.data?.let {
                trainingsLiveDataWrapper.update(it)
            }
        }
    }

    fun getCurrentParent(): Parent? {
        val user = currentUserLiveDataWrapper.liveData().value
        if (user is AuthUser.ParentUser) {
            return user.parent
        }
        return null
    }

    fun futureTrainingsLiveData() = trainingsLiveDataWrapper.liveData()
}