package com.example.disputer.schedule.presentation

import androidx.lifecycle.ViewModel
import com.example.disputer.about_trainings.AboutTrainingScreen
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.core.Navigation
import com.example.disputer.parent.data.Parent
import com.example.disputer.schedule.domain.ClickedTrainingToSignUpLiveDataWrapper
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.training.domain.repository.utils.ClickedTrainingLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.FutureTrainingListLiveDataWrapper
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingScreen
import com.example.disputer.training.presentation.training_sign_up.TrainingSignUpScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel(
    private val navigation: Navigation,
    private val trainingsRepository: TrainingsRepository,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val futureTrainingListLiveDataWrapper: FutureTrainingListLiveDataWrapper,
    //private val clickedTrainingToSignUpLiveDataWrapper: ClickedTrainingToSignUpLiveDataWrapper,
    private val clickedTrainingLiveDataWrapper: ClickedTrainingLiveDataWrapper,
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
            val trainings = trainingsRepository.getFutureTrainings().data

            withContext(dispatcherMain) {
                trainings?.let {
                    futureTrainingListLiveDataWrapper.update(trainings)
                }
            }
        }
    }

    fun observeTrainingsAndShops() {
        trainingsRepository.observeTrainingsLiveData().observeForever {
            it.data?.let {
                futureTrainingListLiveDataWrapper.update(it)
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

    fun trainingSignUpScreen(training: Training) {
        //clickedTrainingToSignUpLiveDataWrapper.update(training)
        if (getCurrentParent() != null) {
            clickedTrainingLiveDataWrapper.update(training)
            navigation.update(TrainingSignUpScreen)
        } else {
            clickedTrainingLiveDataWrapper.update(training)
            navigation.update(AddTrainingScreen)
        }
    }

    fun futureTrainingsLiveData() = futureTrainingListLiveDataWrapper.liveData()

    fun aboutTrainingsScreen() = navigation.update(AboutTrainingScreen)
}