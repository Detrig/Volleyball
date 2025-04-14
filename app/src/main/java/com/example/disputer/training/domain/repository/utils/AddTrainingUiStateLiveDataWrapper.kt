package com.example.disputer.training.domain.repository.utils

import com.example.disputer.core.LiveDataWrapper
import com.example.disputer.training.presentation.training_coach.add_training.AddTrainingUiState

interface AddTrainingUiStateLiveDataWrapper : LiveDataWrapper.Mutable<AddTrainingUiState> {

    class Base : AddTrainingUiStateLiveDataWrapper, LiveDataWrapper.Abstract<AddTrainingUiState>()
}