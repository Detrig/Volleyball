package com.example.disputer.authentication.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentRegistrationBinding

class RegisterFragment : AbstractFragment<FragmentRegistrationBinding>() {

    private lateinit var viewModel : RegisterViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding =
        FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(RegisterViewModel::class.java)

        viewModel.liveData().observe(viewLifecycleOwner) { uiState ->
            uiState.update(binding)
        }
        viewModel.init()

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val repeatPassword = binding.repeatPasswordEditText.text.toString()

            viewModel.register(email, password, repeatPassword)
        }

        binding.loginTV.setOnClickListener {
            viewModel.loginScreen()
        }

    }
}