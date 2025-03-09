package com.example.disputer.core

import androidx.lifecycle.ViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(
        private val clear: ClearViewModel
    ) : ProvideViewModel {

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                //todo
                else -> throw IllegalStateException("unknown viewModelClass $viewModelClass")
            } as T

        }
    }
}