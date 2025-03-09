package com.example.disputer

import com.example.disputer.databinding.FragmentLoginBinding

interface LoginUiState {

    fun update(binding : FragmentLoginBinding) : Unit = throw IllegalStateException("")

    object Initial : LoginUiState {

    }

    object Loading : LoginUiState{

    }

    object Success : LoginUiState {

    }

    class Error(val errorText: String) : LoginUiState {

    }
}
