package com.example.disputer.children.presentation.add

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.children.domain.utils.AddChildrenUiStateLiveDataWrapper
import com.example.disputer.children.domain.utils.ClickedChildrenLiveDataWrapper
import com.example.disputer.core.Navigation
import com.example.disputer.core.Resource
import com.example.disputer.core.Screen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddChildrenViewModel(
    private val navigation: Navigation,
    private val childrenRepository: ChildrenRepository,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val clickedChildrenLiveDataWrapper: ClickedChildrenLiveDataWrapper,
    private val addChildrenUiStateLiveDataWrapper: AddChildrenUiStateLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun addChild(children: Student) {
        addChildrenUiStateLiveDataWrapper.update(AddChildrenUiState.Loading)
        viewModelScope.launch(dispatcherIO) {
            val result = childrenRepository.addChildren(children)
            withContext(dispatcherMain) {
                if (result is Resource.Success)
                    navigation.update(Screen.Pop)
                else if (result is Resource.Error)
                    addChildrenUiStateLiveDataWrapper.update(AddChildrenUiState.Error(result.message!!))
            }
        }
    }

    fun clickedChildrenLiveData() = clickedChildrenLiveDataWrapper.liveData()
    fun clearClickedChildrenLiveData() = clickedChildrenLiveDataWrapper.update(Student())

    fun addChildrenUiStateLiveData() = addChildrenUiStateLiveDataWrapper.liveData()

    fun getCurrentParentId() : String {
        var id = ""
        currentUserLiveDataWrapper.liveData().value?.let {
            if (it is AuthUser.ParentUser)
                id = it.parent.uid
        }
        return id
    }

}