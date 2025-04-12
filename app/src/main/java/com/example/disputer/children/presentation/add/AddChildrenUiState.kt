package com.example.disputer.children.presentation.add

import android.view.View
import android.widget.Toast
import com.example.disputer.databinding.FragmentAddChildrenBinding

interface AddChildrenUiState {

    fun update(
        binding: FragmentAddChildrenBinding
    ) {
        binding.addPhotoContainer.visibility = View.VISIBLE
        binding.fieldsContainer.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    object Initial : AddChildrenUiState

    object Loading : AddChildrenUiState {
        override fun update(binding: FragmentAddChildrenBinding) {
            binding.addPhotoContainer.visibility = View.INVISIBLE
            binding.fieldsContainer.visibility = View.INVISIBLE
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