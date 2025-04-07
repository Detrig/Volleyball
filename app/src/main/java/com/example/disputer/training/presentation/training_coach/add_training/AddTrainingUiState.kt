package com.example.disputer.training.presentation.training_coach.add_training

import android.view.View
import android.widget.Toast
import com.example.disputer.databinding.FragmentAddShopBinding
import com.example.disputer.databinding.FragmentAddTrainingBinding

interface AddTrainingUiState {

    fun update(
        binding: FragmentAddTrainingBinding
    ) {
        binding.addTrainingContainer.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    object Initial : AddTrainingUiState

    object Loading : AddTrainingUiState {
        override fun update(binding: FragmentAddTrainingBinding) {
            binding.addTrainingContainer.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    data class Error(val errorText: String) : AddTrainingUiState {
        override fun update(binding: FragmentAddTrainingBinding) {
            super.update(binding)
            if (errorText != null)
                Toast.makeText(binding.root.context, errorText, Toast.LENGTH_SHORT).show()
        }
    }
}