package com.example.disputer.training.presentation.training_coach

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.domain.usecases.GetParentByChildIdUseCase
import com.example.disputer.coach.data.Coach
import com.example.disputer.core.Navigation
import com.example.disputer.core.Screen
import com.example.disputer.notification.data.FirebaseNotificationRepository
import com.example.disputer.notification.data.NotificationData
import com.example.disputer.notification.domain.repo.NotificationRepository
import com.example.disputer.notification.domain.utils.NotificationHelper
import com.example.disputer.shop.domain.repo.ShopRepository
import com.example.disputer.shop.presentation.AddShopScreen
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.utils.AddTrainingUiStateLiveDataWrapper
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.shop.domain.utils.ShopsLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.ClickedTrainingLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.SignedUpForTrainingChildrensByParentLiveDataWrapper
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingScreen
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingUiState
import com.example.disputer.training.presentation.training_coach.main.TrainingCoachMainScreen
import com.example.disputer.training.domain.repository.utils.TrainingsLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingCoachViewModel(
    private val navigation: Navigation,
    private val trainingsRepository: TrainingsRepository,
    private val shopRepository: ShopRepository,
    private val notificationRepository: NotificationRepository,
    private val trainingsLiveDataWrapper: TrainingsLiveDataWrapper,
    private val shopsLiveDataWrapper: ShopsLiveDataWrapper,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val addTrainingUiStateLiveDataWrapper: AddTrainingUiStateLiveDataWrapper,
    private val clickedTrainingLiveDataWrapper: ClickedTrainingLiveDataWrapper,
    private val signedUpForTrainingChildrensByParentLiveDataWrapper: SignedUpForTrainingChildrensByParentLiveDataWrapper,
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

    fun getCoachAddresses(): List<String> {
        return currentCoach()?.address ?: emptyList()
    }

    fun getChildrenSignedUpForTraining(trainingId: String) {
        viewModelScope.launch(dispatcherIO) {
            val resultSignedUpChildrens = trainingsRepository.getChildrensSignedUpForTraining(trainingId).data ?: listOf()
            Log.d("VB-14", "signed up childrens: $resultSignedUpChildrens, trainingId: $trainingId")
            withContext(dispatcherMain) {
                signedUpForTrainingChildrensByParentLiveDataWrapper.update(resultSignedUpChildrens)
            }
        }
    }

    private fun currentCoach(): Coach? {
        val currentUser = currentUserLiveDataWrapper.liveData().value
        if (currentUser is AuthUser.CoachUser) {
            return currentUser.coach
        }
        return null
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

    fun deleteTraining(training: Training, notificationHelper: NotificationHelper) {
        viewModelScope.launch(dispatcherIO) {

            val signedUpChildrens = trainingsRepository.getChildrensSignedUpForTraining(training.id).data ?: listOf()

            val parentIds = signedUpChildrens.map { it.parentId }

            notificationRepository.sendNotification(training.id, training.date, parentIds.toSet().toList())

            parentIds.forEach { parentId ->
                val notificationData = NotificationData(
                    id = training.id,
                    title = "Тренировка отменена",
                    body = "Тренировка ${training.date} была отменена.",
                    timestamp = System.currentTimeMillis()
                )
                notificationHelper.showNotification(notificationData)
            }

            trainingsRepository.deleteTraining(training)
            withContext(dispatcherMain) {
                navigation.update(Screen.Pop)
            }
        }
    }

    fun addTrainingScreen(training: Training = Training()) {
        if (training.id != "") {
            clickedTrainingLiveDataWrapper.update(training)
            Log.d(
                "VB-03",
                "Not null: $training, currentTraining: ${clickedTrainingLiveData().value}"
            )
            navigation.update(AddTrainingScreen)
        } else {
            Log.d("VB-03", "Null: $training, currentTraining: ${clickedTrainingLiveData().value}")
            navigation.update(AddTrainingScreen)
        }
    }

    fun addShopScreen() = navigation.update(AddShopScreen)

    fun addTrainingUiStateLiveData() = addTrainingUiStateLiveDataWrapper.liveData()
    fun trainingsLiveData() = trainingsLiveDataWrapper.liveData()
    fun shopsLiveData() = shopsLiveDataWrapper.liveData()
    fun signedUpForTrainingChildrensByParentLiveData() = signedUpForTrainingChildrensByParentLiveDataWrapper.liveData()

    fun clickedTrainingLiveData() = clickedTrainingLiveDataWrapper.liveData()
    fun clearClickedTrainingLiveData() = clickedTrainingLiveDataWrapper.update(Training())

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