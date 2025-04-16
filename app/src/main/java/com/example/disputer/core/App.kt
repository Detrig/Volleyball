package com.example.disputer.core

import android.app.Application
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MemoryCacheSettings
import com.google.firebase.firestore.PersistentCacheSettings

class App : Application(), ProvideViewModel {

    private lateinit var factory: ViewModelFactory

    private val clear: ClearViewModel = object : ClearViewModel {
        override fun clearViewModel(viewModelClass: Class<out ViewModel>) {
            factory.clearViewModel(viewModelClass)
        }
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        val firestore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build()) // Включение кэша в памяти
            .setLocalCacheSettings(PersistentCacheSettings.newBuilder().build()) // Включение дискового кэша
            .build()
        firestore.firestoreSettings = settings

        val provideViewModel = ProvideViewModel.Base(clear)
        factory = ViewModelFactory.Base(provideViewModel)

    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        factory.viewModel(viewModelClass)

}