package com.example.disputer.authentication.presentation.main

import androidx.lifecycle.ViewModel
import com.example.disputer.disputes.presentation.activedispute.ActiveDisputeScreen
import com.example.disputer.authentication.presentation.login.LoginScreen
import com.example.disputer.core.Navigation

class MainViewModel(
    private val navigation: Navigation
) : ViewModel() {

    fun login() {
        navigation.update(LoginScreen)
    }

    fun init(firstRun: Boolean) {
        navigation.update(ActiveDisputeScreen)
    }

    fun liveData() = navigation.liveData()
}