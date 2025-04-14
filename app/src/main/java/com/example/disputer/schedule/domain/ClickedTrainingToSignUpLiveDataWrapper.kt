package com.example.disputer.schedule.domain

import com.example.disputer.core.LiveDataWrapper
import com.example.disputer.training.data.Training

interface ClickedTrainingToSignUpLiveDataWrapper : LiveDataWrapper.Mutable<Training> {
    class Base : ClickedTrainingToSignUpLiveDataWrapper, LiveDataWrapper.Abstract<Training>()
}