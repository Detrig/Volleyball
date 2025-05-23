package com.example.disputer.children.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.utils.ClickedChildrenLiveDataWrapper
import com.example.disputer.children.presentation.add.AddChildrenScreen
import com.example.disputer.core.Navigation
import com.example.disputer.info.InfoScreen
import com.example.disputer.parent.data.Parent
import com.example.disputer.parent.domain.usecase.GetParentChildrensUseCase
import com.example.disputer.parent.domain.utils.ParentChildsListLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChildrenViewModel(
    private val navigation: Navigation,
    private val clickedChildrenLiveDataWrapper: ClickedChildrenLiveDataWrapper,
    private val getParentChildrensUseCase: GetParentChildrensUseCase,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val parentChildsListLiveDataWrapper: ParentChildsListLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun addChildrenScreen(children: Student = Student()) {
        if (children.uid.isEmpty())
            navigation.update(AddChildrenScreen)
        else {
            clickedChildrenLiveDataWrapper.update(children)
            navigation.update(AddChildrenScreen)
        }
    }

    fun getParentChilds(parentId: String) {
        viewModelScope.launch(dispatcherIo) {
            val childrens = getParentChildrensUseCase.invoke(parentId).data ?: listOf()
            withContext(dispatcherMain) {
                parentChildsListLiveDataWrapper.update(childrens)
            }
        }
    }

    fun getCurrentParent() : Parent? {
        val user = currentUserLiveDataWrapper.liveData().value
        if (user is AuthUser.ParentUser) {
            return user.parent
        }
        return null
    }

    fun parentChildsListLiveData() = parentChildsListLiveDataWrapper.liveData()
    fun clearClickedChildrenLiveData() = clickedChildrenLiveDataWrapper.update(Student())
}
