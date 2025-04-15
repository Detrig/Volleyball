package com.example.disputer.info

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentInformationBinding
import com.google.android.gms.auth.api.Auth

class InfoFragment : AbstractFragment<FragmentInformationBinding>() {

    private lateinit var viewModel: InfoViewModel

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentInformationBinding =
        FragmentInformationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(InfoViewModel::class.java)
        val currentUser = viewModel.currentUser()

        setUpViews(currentUser)
        setClickListeners(currentUser)
    }

    private fun setUpViews(currentUser : AuthUser?) {
        Log.d("VB-06", "SetUpViews ${currentUser.toString()}")

        if (currentUser is AuthUser.ParentUser) {
            val parent = currentUser.parent
            if (parent.name.isEmpty())
                binding.userNameTV.text = parent.email
            else {
                binding.userNameTV.text = parent.name
                binding.phoneNumberTV.text = parent.phoneNumber
            }

        } else if (currentUser is AuthUser.CoachUser) {
            binding.myChildrenButton.visibility = View.GONE
            binding.myTrainingsButton.visibility = View.GONE
            val coach = currentUser.coach
            if (coach.name.isEmpty())
                binding.userNameTV.text = coach.email
            else {
                binding.userNameTV.text = coach.name
                binding.phoneNumberTV.text = coach.phoneNumber
            }
        }
    }

    private fun setClickListeners(currentUser : AuthUser?) {
        binding.myChildrenButton.setOnClickListener {
            viewModel.childrensScreen()
        }

        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.profileLayout.setOnClickListener {
            Log.d("VB-04", currentUser.toString())
            if (currentUser is AuthUser.CoachUser) {
                viewModel.editCoachProfileScreen()
            } else if (currentUser is AuthUser.ParentUser) {
                viewModel.editParentProfileScreen()
            }
        }

        binding.myTrainingsButton.setOnClickListener {
            viewModel.myAllTrainingParentScreen()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Выход из аккаунта")
            .setMessage("Вы уверены, что хотите выйти из аккаунта?")
            .setPositiveButton("Выйти") { _, _ -> viewModel.logout() }
            .setNegativeButton("Отмена", null)
            .create()
            .show()
    }
}