package com.example.disputer.coach.domain.utils

import com.example.disputer.coach.data.Coach
import com.example.disputer.core.LiveDataWrapper

interface ClickedCoachLiveDataWrapper : LiveDataWrapper.Mutable<Coach> {
    class Base : ClickedCoachLiveDataWrapper, LiveDataWrapper.Abstract<Coach>()
}