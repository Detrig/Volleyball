package com.example.disputer.shop.presentation

import android.view.View
import android.widget.Toast
import com.example.disputer.databinding.FragmentAddShopBinding

interface AddShopUiState {

    fun update(
        binding: FragmentAddShopBinding
    ) {
        binding.addShopContainer.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    object Initial : AddShopUiState

    object Loading : AddShopUiState {
        override fun update(binding: FragmentAddShopBinding) {
            binding.addShopContainer.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    data class Error(val errorText: String) : AddShopUiState {
        override fun update(binding: FragmentAddShopBinding) {
            super.update(binding)
            if (errorText != null)
                Toast.makeText(binding.root.context, errorText, Toast.LENGTH_SHORT).show()
        }
    }
}