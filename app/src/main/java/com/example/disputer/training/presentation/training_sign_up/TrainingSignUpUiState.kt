package com.example.disputer.training.presentation.training_sign_up

import com.example.disputer.databinding.FragmentSignUpTrainingBinding

interface TrainingSignUpUiState {

    fun update(binding: FragmentSignUpTrainingBinding)

    object Loading


}