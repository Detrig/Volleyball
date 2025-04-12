package com.example.disputer.training.domain.repository.utils

import com.example.disputer.core.LiveDataWrapper
import com.example.disputer.training.data.Training

interface ClickedTrainingLiveDataWrapper : LiveDataWrapper.Mutable<Training> {

    class Base : ClickedTrainingLiveDataWrapper, LiveDataWrapper.Abstract<Training>()
}