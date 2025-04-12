package com.example.disputer.coach.presentation.edit_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.coach.data.Coach
import com.example.disputer.coach.domain.usecase.UpdateCoachUseCase
import com.example.disputer.core.Navigation
import com.example.disputer.core.Screen
import com.example.disputer.info.InfoScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditCoachProfileViewModel(
    private val navigation: Navigation,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val updateCoachUseCase: UpdateCoachUseCase,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun updateCoach(coach: Coach) {
        Log.d("VB-05", "EditCoachProfileViewModel ${coach.toString()}")
        viewModelScope.launch(dispatcherIo) {
            updateCoachUseCase.invoke(coach)

            withContext(dispatcherMain) {
                currentUserLiveDataWrapper.update(AuthUser.CoachUser(coach))
                navigation.update(InfoScreen)
            }
        }
    }

    fun currentCoach() : Coach? {
        val currentUser = currentUserLiveDataWrapper.liveData().value
        if (currentUser is AuthUser.CoachUser) {
            return currentUser.coach
        }
        return null
    }
}