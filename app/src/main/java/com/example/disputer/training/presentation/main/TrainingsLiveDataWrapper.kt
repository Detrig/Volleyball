package com.example.disputer.training.presentation.main

import com.example.disputer.authentication.data.Training
import com.example.disputer.core.LiveDataWrapper

interface TrainingsLiveDataWrapper : LiveDataWrapper.Mutable<List<Training>> {

    class Base : TrainingsLiveDataWrapper, LiveDataWrapper.Abstract<List<Training>>()
}