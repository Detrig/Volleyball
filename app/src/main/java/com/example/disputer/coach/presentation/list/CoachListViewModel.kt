package com.example.disputer.coach.presentation.list

import androidx.lifecycle.ViewModel
import com.example.disputer.coach.data.Coach
import com.example.disputer.coach.domain.usecase.GetCoachListUseCase
import com.example.disputer.coach.domain.utils.ClickedCoachLiveDataWrapper
import com.example.disputer.coach.domain.utils.CoachListLiveDataWrapper
import com.example.disputer.coach.presentation.profile.CoachInfoViewModel
import com.example.disputer.coach.presentation.profile.CoachProfileInfoScreen
import com.example.disputer.core.Navigation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoachListViewModel(
    private val navigation: Navigation,
    private val getCoachListUseCase: GetCoachListUseCase,
    private val clickedCoachLiveDataWrapper: ClickedCoachLiveDataWrapper,
    private val coachListLiveDataWrapper: CoachListLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        loadCoachs()
    }

    private fun loadCoachs() {
        viewModelScope.launch(dispatcherIo) {
            getCoachListUseCase.invoke().data?.let {
                withContext(dispatcherMain) {
                    coachListLiveDataWrapper.update(it)
                }
            }
        }
    }

    fun coachProfileScreen(coach: Coach) {
        clickedCoachLiveDataWrapper.update(coach)
        navigation.update(CoachProfileInfoScreen)
    }


    fun coachListLiveData() = coachListLiveDataWrapper.liveData()
}