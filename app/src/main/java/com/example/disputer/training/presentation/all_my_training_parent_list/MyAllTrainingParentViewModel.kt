package com.example.disputer.training.presentation.all_my_training_parent_list

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.domain.usecases.GetChildrenByIdUseCase
import com.example.disputer.children.domain.usecases.GetChildrenTrainings
import com.example.disputer.core.Navigation
import com.example.disputer.parent.data.Parent
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.utils.YourChildrenTrainingLiveLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyAllTrainingParentViewModel(
    private val getChildrenByIdUseCase: GetChildrenByIdUseCase,
    private val getChildrenTrainings: GetChildrenTrainings,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val yourChildrenTrainingLiveLiveDataWrapper: YourChildrenTrainingLiveLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun getYourChildrenAllTrainings() {
        viewModelScope.launch(dispatcherIO) {
            val currentParent = getCurrentParent() ?: Parent()
            val allTrainings = mutableSetOf<Training>()
            currentParent.childIds.forEach { childId ->
                val children = getChildrenByIdUseCase.invoke(childId).data
                children?.let {
                    val trainings = getChildrenTrainings.invoke(it).data ?: emptyList()
                    allTrainings.addAll(trainings)
                }
            }
            withContext(dispatcherMain) {
                yourChildrenTrainingLiveLiveDataWrapper.update(allTrainings.toList())
            }
        }
    }

    private fun getCurrentParent(): Parent? {
        val user = currentUserLiveDataWrapper.liveData().value
        if (user is AuthUser.ParentUser) {
            return user.parent
        }
        return null
    }

    fun yourChildrenTrainingLiveLiveData() = yourChildrenTrainingLiveLiveDataWrapper.liveData()
}