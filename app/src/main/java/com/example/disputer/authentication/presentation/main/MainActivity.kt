package com.example.disputer.authentication.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.example.disputer.R
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.ActivityMainBinding
import com.example.disputer.notification.domain.utils.NotificationObserver
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        viewModel = viewModel(MainViewModel::class.java)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            NotificationObserver.observeNotifications(this, userId)
        }

        viewModel.liveData().observe(this) { screen ->
            screen.show(supportFragmentManager, R.id.main)
        }
        viewModel.init(savedInstanceState == null)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> viewModel.mainScreen()
                R.id.schedule -> viewModel.scheduleScreen()
                R.id.coach -> viewModel.coachScreen()
                R.id.info -> viewModel.infoScreen()
            }
            true
        }
    }


    fun hideHeaderBottomNav() {
        binding.headerImage.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE
        binding.constraint.setBackgroundColor(resources.getColor(R.color.white))
    }

    fun showHeaderBottomNav() {
        binding.headerImage.visibility = View.VISIBLE
        binding.bottomNavigation.visibility = View.VISIBLE
        binding.constraint.setBackgroundColor(resources.getColor(R.color.dark_blue))
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        (application as ProvideViewModel).viewModel(viewModelClass)

}