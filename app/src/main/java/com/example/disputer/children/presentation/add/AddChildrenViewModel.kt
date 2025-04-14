package com.example.disputer.children.presentation.add

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.children.domain.usecases.AddChildrenUseCase
import com.example.disputer.children.domain.usecases.DeleteChildrenUseCase
import com.example.disputer.children.domain.utils.AddChildrenUiStateLiveDataWrapper
import com.example.disputer.children.domain.utils.ClickedChildrenLiveDataWrapper
import com.example.disputer.core.Navigation
import com.example.disputer.core.Resource
import com.example.disputer.core.Screen
import com.example.disputer.parent.data.Parent
import com.example.disputer.parent.domain.utils.ParentChildsListLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddChildrenViewModel(
    private val navigation: Navigation,
    private val addChildrenUseCase: AddChildrenUseCase,
    private val deleteChildrenUseCase: DeleteChildrenUseCase,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val clickedChildrenLiveDataWrapper: ClickedChildrenLiveDataWrapper,
    private val parentChildsListLiveDataWrapper: ParentChildsListLiveDataWrapper,
    private val addChildrenUiStateLiveDataWrapper: AddChildrenUiStateLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun addChild(children: Student) {
        addChildrenUiStateLiveDataWrapper.update(AddChildrenUiState.Loading)
        val parent = getCurrentParent()
        viewModelScope.launch(dispatcherIO) {
            parent?.let {
                val result = addChildrenUseCase.invoke(it.uid, children)
                withContext(dispatcherMain) {
                    if (result is Resource.Success) {
                        val newUid = result.data?.first ?: children.uid
                        val studentWithRealUid = children.copy(uid = newUid)

                        parentChildsListLiveDataWrapper.add(studentWithRealUid)
                        addChildrenToCurrentParent(studentWithRealUid)
                        navigation.update(Screen.Pop)
                    } else if (result is Resource.Error)
                        addChildrenUiStateLiveDataWrapper.update(AddChildrenUiState.Error(result.message!!))
                }
            }
        }
    }

    fun clickedChildrenLiveData() = clickedChildrenLiveDataWrapper.liveData()
    fun clearClickedChildrenLiveData() = clickedChildrenLiveDataWrapper.update(Student())

    fun addChildrenUiStateLiveData() = addChildrenUiStateLiveDataWrapper.liveData()

    fun getCurrentParent(): Parent? {
        val user = currentUserLiveDataWrapper.liveData().value
        if (user is AuthUser.ParentUser) {
            return user.parent
        }
        return null
    }

    fun getCurrentParentId(): String {
        var id = ""
        currentUserLiveDataWrapper.liveData().value?.let {
            if (it is AuthUser.ParentUser)
                id = it.parent.uid
        }
        return id
    }

    fun deleteChildren(children: Student) {
        addChildrenUiStateLiveDataWrapper.update(AddChildrenUiState.Loading)
        viewModelScope.launch(dispatcherIO) {
            deleteChildrenUseCase.invoke(children)
            withContext(dispatcherMain) {
                val childs = parentChildsListLiveDataWrapper.liveData().value?.toMutableList() ?: mutableListOf()
                childs.remove(children)
                Log.d("VB-10", "new childs: $childs")
                parentChildsListLiveDataWrapper.update(childs)
                navigation.update(Screen.Pop)
            }
        }
    }

    private fun addChildrenToCurrentParent(child: Student) {
        val oldChildIds = getCurrentParent()?.childIds ?: listOf()
        val newChildIds = oldChildIds.toMutableList().apply { add(child.uid) }

        val parentWithNewList = getCurrentParent()?.copy(childIds = newChildIds) ?: Parent()
        Log.d("VB-09", "child: $child parentWithNewList $parentWithNewList")
        currentUserLiveDataWrapper.update(AuthUser.ParentUser(parentWithNewList))
    }
}