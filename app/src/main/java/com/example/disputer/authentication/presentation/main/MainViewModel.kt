package com.example.disputer.authentication.presentation.main

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.authentication.presentation.login.LoginScreen
import com.example.disputer.core.Navigation
import com.example.disputer.coach.presentation.list.CoachScreen
import com.example.disputer.info.InfoScreen
import com.example.disputer.training.presentation.training_parent.TrainingParentMainScreen
import com.example.disputer.schedule.presentation.ScheduleScreen
import com.example.disputer.training.presentation.training_coach.main.TrainingCoachMainScreen

class MainViewModel(
    private val navigation: Navigation,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper
) : ViewModel() {

    fun login() {
        navigation.update(LoginScreen)
    }

    fun getCurrentUserRole() = currentUserLiveDataWrapper.liveData().value

    fun mainScreen() {
        if (getCurrentUserRole() is AuthUser.ParentUser)
            navigation.update(TrainingParentMainScreen)
        else if (getCurrentUserRole() is AuthUser.CoachUser)
            navigation.update(TrainingCoachMainScreen)
    }

    fun scheduleScreen() {
        navigation.update(ScheduleScreen)
    }

    fun coachScreen() {
        navigation.update(CoachScreen)
    }

    fun infoScreen() {
        navigation.update(InfoScreen)
    }

    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(LoginScreen)
    }

    fun liveData() = navigation.liveData()
}