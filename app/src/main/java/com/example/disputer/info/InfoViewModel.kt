package com.example.disputer.info

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.usecase.LogoutUseCase
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.authentication.presentation.login.LoginScreen
import com.example.disputer.children.presentation.add.AddChildrenScreen
import com.example.disputer.children.presentation.list.ChildrensScreen
import com.example.disputer.coach.presentation.edit_profile.EditCoachProfileScreen
import com.example.disputer.core.Navigation
import com.example.disputer.parent.presentation.edit_profile.EditParentProfileScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoViewModel(
    private val navigation: Navigation,
    private val logoutUseCase: LogoutUseCase,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun editCoachProfileScreen() = navigation.update(EditCoachProfileScreen)
    fun editParentProfileScreen() = navigation.update(EditParentProfileScreen)
    fun childrensScreen() = navigation.update(ChildrensScreen)

    fun currentUser() = currentUserLiveDataWrapper.liveData().value

    fun logout() {
        viewModelScope.launch(dispatcherIo) {
            logoutUseCase.invoke()

            withContext(dispatcherMain) {
                currentUserLiveDataWrapper.update(AuthUser.Empty)
                navigation.update(LoginScreen)
            }
        }
    }
}