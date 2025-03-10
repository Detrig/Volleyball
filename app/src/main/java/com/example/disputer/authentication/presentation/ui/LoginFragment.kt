package com.example.disputer.authentication.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.authentication.presentation.viewmodel.LoginViewModel
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentLoginBinding


class LoginFragment : AbstractFragment<FragmentLoginBinding>() {

    private lateinit var viewModel : LoginViewModel
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding =
        FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(LoginViewModel::class.java)

        viewModel.liveDataUiState().observe(viewLifecycleOwner) { uiState ->
            uiState.update(binding)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password= binding.passwordEditText.text.toString()

            hideKeyBoard()

            viewModel.login(email, password)
        }
    }
}