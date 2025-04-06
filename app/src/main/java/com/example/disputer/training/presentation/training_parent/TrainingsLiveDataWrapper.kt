package com.example.disputer.training.presentation.training_parent

import com.example.disputer.training.data.Training
import com.example.disputer.core.LiveDataWrapper

interface TrainingsLiveDataWrapper : LiveDataWrapper.Mutable<List<Training>> {

    class Base : TrainingsLiveDataWrapper, LiveDataWrapper.Abstract<List<Training>>()
}