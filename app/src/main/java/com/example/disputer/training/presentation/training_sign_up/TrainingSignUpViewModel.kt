package com.example.disputer.training.presentation.training_sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.utils.CurrentParentChildrenListLiveDataWrapper
import com.example.disputer.core.Navigation
import com.example.disputer.core.Resource
import com.example.disputer.core.Screen
import com.example.disputer.parent.domain.usecase.GetParentChildrensUseCase
import com.example.disputer.schedule.domain.ClickedTrainingToSignUpLiveDataWrapper
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.training.domain.repository.utils.ClickedTrainingLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.SignedUpForTrainingChildrensLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingSignUpViewModel(
    private val navigation: Navigation,
    private val trainingRepository: TrainingsRepository,
    private val getParentChildrensUseCase: GetParentChildrensUseCase,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val currentParentChildrenListLiveDataWrapper: CurrentParentChildrenListLiveDataWrapper,
    private val clickedTrainingToSignUpLiveDataWrapper: ClickedTrainingToSignUpLiveDataWrapper,
    private val signedUpForTrainingChildrensLiveDataWrapper: SignedUpForTrainingChildrensLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain : CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo : CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _signUpMessageLiveData = MutableLiveData<String>()
    val signUpMessageLiveData: LiveData<String> = _signUpMessageLiveData

    private val currentUser = currentUserLiveDataWrapper.liveData().value

    fun loadChildren(trainingId: String) {
        viewModelScope.launch(dispatcherIo) {
            val parentId = getCurrentParentId()
            val childrens = getParentChildrensUseCase.invoke(parentId).data

            withContext(dispatcherMain) {
                childrens?.let {
                    currentParentChildrenListLiveDataWrapper.update(childrens)
                    Log.d("VB-13", "currentParentChildren: $it")
                    childrens.forEach { children ->
                        children.trainingIds.forEach {
                            if (it == trainingId)
                                Log.d("VB-13", "signedUpChildren: ${children}")
                                signedUpForTrainingChildrensLiveDataWrapper.add(children)
                        }
                    }
                }
            }
        }
    }

    fun signUpChildren(trainingId: String, students: List<Student>) {
        viewModelScope.launch(dispatcherIo) {
            val result = trainingRepository.signUpForTraining(trainingId, students.map { it.uid })

            withContext(dispatcherMain) {
                when (result) {
                    is Resource.Success -> {
                        //sendNotificationToCoach(trainingId, students)
                        _signUpMessageLiveData.postValue("Вы успешно записались на тренировку ✅")
                        navigation.update(Screen.Pop)
                    }
                    is Resource.Error -> {
                        _signUpMessageLiveData.postValue(result.message)
                    }
                }
            }
        }
    }

    fun getCurrentParentId(): String {
        var id = ""
        currentUserLiveDataWrapper.liveData().value?.let {
            if (it is AuthUser.ParentUser)
                id = it.parent.uid
        }
        return id
    }

    fun clickedTrainingToSignUpLiveData() = clickedTrainingToSignUpLiveDataWrapper.liveData()
    fun currentParentChildrenListLiveData() = currentParentChildrenListLiveDataWrapper.liveData()
    fun signedUpForTrainingChildrensLiveData() = signedUpForTrainingChildrensLiveDataWrapper.liveData()

}