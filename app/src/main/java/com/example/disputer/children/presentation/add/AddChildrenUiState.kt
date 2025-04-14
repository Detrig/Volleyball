package com.example.disputer.children.presentation.add

import android.view.View
import android.widget.Toast
import com.example.disputer.databinding.FragmentAddChildrenBinding

interface AddChildrenUiState {

    fun update(
        binding: FragmentAddChildrenBinding
    ) {
        binding.addPhotoContainer.visibility = View.VISIBLE
        binding.nameEditText.visibility = View.VISIBLE
        binding.phoneEditText.visibility = View.VISIBLE
        binding.ageEditText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    object Initial : AddChildrenUiState

    object Loading : AddChildrenUiState {
        override fun update(binding: FragmentAddChildrenBinding) {
            binding.addPhotoContainer.visibility = View.INVISIBLE
            binding.nameEditText.visibility = View.INVISIBLE
            binding.phoneEditText.visibility = View.INVISIBLE
            binding.ageEditText.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    data class Error(val errorText: String) : AddChildrenUiState {
        override fun update(binding: FragmentAddChildrenBinding) {
            super.update(binding)
            if (errorText != null)
                Toast.makeText(binding.root.context, errorText, Toast.LENGTH_SHORT).show()
        }
    }
}