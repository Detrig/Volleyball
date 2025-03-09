package com.example.disputer.core

import android.app.Application
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp

class App : Application(), ProvideViewModel {

    private lateinit var factory: ViewModelFactory

    private val clear: ClearViewModel = object : ClearViewModel {
        override fun clearViewModel(viewModelClass: Class<out ViewModel>) {
            factory.clearViewModel(viewModelClass)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val provideViewModel = ProvideViewModel.Base(clear)
        factory = ViewModelFactory.Base(provideViewModel)
        FirebaseApp.initializeApp(this)
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        factory.viewModel(viewModelClass)

}