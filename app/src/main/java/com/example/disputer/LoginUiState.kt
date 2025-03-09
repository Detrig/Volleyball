package com.example.disputer

import com.example.disputer.databinding.FragmentLoginBinding

interface LoginUiState {

    fun update(binding : FragmentLoginBinding) : Unit = throw IllegalStateException("")

    object Initial : LoginUiState {

        override fun update(binding: FragmentLoginBinding) {
            super.update(binding)
        }
    }

    object Loading : LoginUiState{
        override fun update(binding: FragmentLoginBinding) {
            super.update(binding)
        }
    }

    object Success : LoginUiState {
        override fun update(binding: FragmentLoginBinding) {
            super.update(binding)
        }
    }

    class Error(val errorText: String) : LoginUiState {
        override fun update(binding: FragmentLoginBinding) {
            super.update(binding)
        }
    }
}
