package com.example.disputer.parent.presentation.edit_profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.usecases.GetChildrenByIdUseCase
import com.example.disputer.children.domain.utils.ClickedChildrenLiveDataWrapper
import com.example.disputer.children.presentation.add.AddChildrenScreen
import com.example.disputer.core.Navigation
import com.example.disputer.info.InfoScreen
import com.example.disputer.parent.data.Parent
import com.example.disputer.parent.domain.usecase.GetParentChildrensUseCase
import com.example.disputer.parent.domain.usecase.UpdateParentUseCase
import com.example.disputer.parent.domain.utils.ParentChildsListLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditParentProfileViewModel(
    private val navigation: Navigation,
    private val updateParentUseCase: UpdateParentUseCase,
    private val getChildrenByIdUseCase: GetChildrenByIdUseCase,
    private val getParentChildrensUseCase: GetParentChildrensUseCase,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val clickedChildrenLiveDataWrapper: ClickedChildrenLiveDataWrapper,
    private val parentChildsListLiveDataWrapper: ParentChildsListLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _selectedImageLiveData = MutableLiveData<String?>()
    val selectedImageLiveData: LiveData<String?> = _selectedImageLiveData

    fun setSelectedImage(image: String) {
        _selectedImageLiveData.value = image
    }

    fun parentChildsListLiveData() = parentChildsListLiveDataWrapper.liveData()
    fun clearClickedChildrenLiveData() = clickedChildrenLiveDataWrapper.update(Student())

    fun getCurrentParent() : Parent? {
        val user = currentUserLiveDataWrapper.liveData().value
        if (user is AuthUser.ParentUser) {
            return user.parent
        }
        return null
    }

    fun updateParent(parent: Parent) {
        val updatedParent = parent.copy(image = _selectedImageLiveData.value ?: parent.image)
        viewModelScope.launch(dispatcherIo) {
            updateParentUseCase.invoke(updatedParent)
            withContext(dispatcherMain) {
                Log.d("VB-09", "EditParentProfileViewModel parent: $parent")
                currentUserLiveDataWrapper.update(AuthUser.ParentUser(updatedParent))
                navigation.update(InfoScreen)
            }
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

    fun addChildrenScreen(children: Student = Student()) {
        if (children.uid.isEmpty())
            navigation.update(AddChildrenScreen)
        else {
            clickedChildrenLiveDataWrapper.update(children)
            navigation.update(AddChildrenScreen)
        }
    }

    fun infoScreen() {
        navigation.update(InfoScreen)
    }
}