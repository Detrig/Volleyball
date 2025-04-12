package com.example.disputer.coach.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.disputer.coach.domain.utils.ClickedCoachLiveDataWrapper
import com.example.disputer.core.Navigation

class CoachInfoViewModel(
    private val navigation: Navigation,
    private val clickedCoachLiveDataWrapper: ClickedCoachLiveDataWrapper
) : ViewModel() {

    fun currentCoachLiveData() = clickedCoachLiveDataWrapper.liveData()
}