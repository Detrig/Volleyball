package com.example.disputer.training.presentation.training_sign_up

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.training.domain.repository.utils.ClickedTrainingLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.SignedUpForTrainingChildrensLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingSignUpViewModel(
    private val childrenRepository: ChildrenRepository,
    private val trainingRepository: TrainingsRepository,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val clickedTrainingLiveDataWrapper: ClickedTrainingLiveDataWrapper,
    private val signedUpForTrainingChildrensLiveDataWrapper: SignedUpForTrainingChildrensLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain : CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo : CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val currentUser = currentUserLiveDataWrapper.liveData().value

    fun loadChildren(trainingId: String) : List<Student> {
        val childrenList = mutableListOf<Student>()
        viewModelScope.launch(dispatcherIo) {
            currentUser?.let {
                if (currentUser is AuthUser.ParentUser)
                    currentUser.parent.childIds.forEach { id ->
                        childrenList.add(childrenRepository.getChildrenById(id).data!!)
                    }
            }

            val childrenWithTraining = childrenList.filter { children ->
                children.trainingIds.contains(trainingId)
            }
            withContext(dispatcherMain) {
                signedUpForTrainingChildrensLiveDataWrapper.update(childrenWithTraining)
            }
        }
        return childrenList
    }

    fun signUpChildren(trainingId: String, students: List<Student>) {
        viewModelScope.launch(dispatcherIo) {
            trainingRepository.signUpForTraining(trainingId, students.map { it.uid })
        }
    }

    fun clickedTrainingLiveData() = clickedTrainingLiveDataWrapper.liveData()
    fun signedUpForTrainingChildrensLiveData() = signedUpForTrainingChildrensLiveDataWrapper.liveData()

}