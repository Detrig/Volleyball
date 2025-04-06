package com.example.disputer.authentication.presentation.main

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.presentation.login.LoginScreen
import com.example.disputer.core.Navigation
import com.example.disputer.coach.CoachScreen
import com.example.disputer.info.InfoScreen
import com.example.disputer.training.presentation.training_parent.TrainingParentMainScreen
import com.example.disputer.schedule.ScheduleScreen

class MainViewModel(
    private val navigation: Navigation
) : ViewModel() {

    fun login() {
        navigation.update(LoginScreen)
    }

    fun mainScreen() {
        navigation.update(TrainingParentMainScreen)
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