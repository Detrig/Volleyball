package com.example.disputer.authentication.presentation.main

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.presentation.login.LoginScreen
import com.example.disputer.core.Navigation
import com.example.disputer.training.presentation.coach.CoachScreen
import com.example.disputer.training.presentation.info.InfoScreen
import com.example.disputer.training.presentation.main.TrainingMainScreen
import com.example.disputer.training.presentation.schedule.ScheduleScreen

class MainViewModel(
    private val navigation: Navigation
) : ViewModel() {

    fun login() {
        navigation.update(LoginScreen)
    }

    fun mainScreen() {
        navigation.update(TrainingMainScreen)
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
            navigation.update(TrainingMainScreen)
    }

    fun liveData() = navigation.liveData()
}