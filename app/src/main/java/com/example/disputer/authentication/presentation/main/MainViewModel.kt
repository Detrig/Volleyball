package com.example.disputer.authentication.presentation.main

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.presentation.login.LoginScreen
import com.example.disputer.core.Navigation
import com.example.disputer.training.presentation.coach.CoachScreen
import com.example.disputer.training.presentation.info.InfoScreen
import com.example.disputer.training.presentation.main.TrainingMainScreen

class MainViewModel(
    private val navigation: Navigation
) : ViewModel() {

    fun login() {
        navigation.update(LoginScreen)
    }

    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(TrainingMainScreen)
    }

    fun liveData() = navigation.liveData()
}