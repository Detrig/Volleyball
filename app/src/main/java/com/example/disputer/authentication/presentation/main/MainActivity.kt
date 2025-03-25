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

        viewModel.liveData().observe(this) { screen ->
            screen.show(supportFragmentManager, R.id.main)
        }
        viewModel.init(savedInstanceState == null)
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