package com.example.disputer.coach.domain.utils

import com.example.disputer.coach.data.Coach
import com.example.disputer.core.ListLiveDataWrapper

interface CoachListLiveDataWrapper : ListLiveDataWrapper.Mutable<Coach> {

    class Base : CoachListLiveDataWrapper, ListLiveDataWrapper.Abstract<Coach>()
}