package com.example.disputer.info

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.disputer.R
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ImageUtils
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

    private fun setUpViews(currentUser: AuthUser?) {
        if (currentUser is AuthUser.ParentUser) {
            val parent = currentUser.parent

            binding.userNameTV.text = if (parent.name.isEmpty()) parent.email else parent.name
            binding.phoneNumberTV.text = parent.phoneNumber

            if (parent.image.isNotEmpty()) {
                ImageUtils.base64ToBitmap(parent.image)?.let { bitmap ->
                    Glide.with(binding.userImage.context)
                        .load(bitmap)
                        .centerCrop()
                        .placeholder(R.drawable.user)
                        .into(binding.userImage)
                } ?: run {
                    binding.userImage.setImageResource(R.drawable.user)
                }
            } else {
                binding.userImage.setImageResource(R.drawable.user)
            }

        } else if (currentUser is AuthUser.CoachUser) {
            binding.myChildrenButton.visibility = View.GONE
            binding.myTrainingsButton.visibility = View.GONE

            val coach = currentUser.coach

            binding.userNameTV.text = if (coach.name.isEmpty()) coach.email else coach.name
            binding.phoneNumberTV.text = coach.phoneNumber

            if (coach.photoBase64.isNotEmpty()) {
                ImageUtils.base64ToBitmap(coach.photoBase64)?.let { bitmap ->
                    Glide.with(binding.userImage.context)
                        .load(bitmap)
                        .centerCrop()
                        .placeholder(R.drawable.user)
                        .into(binding.userImage)
                } ?: run {
                    binding.userImage.setImageResource(R.drawable.user)
                }
            } else {
                binding.userImage.setImageResource(R.drawable.user)
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
            if (currentUser is AuthUser.CoachUser) {
                viewModel.editCoachProfileScreen()
            } else if (currentUser is AuthUser.ParentUser) {
                viewModel.editParentProfileScreen()
            }
        }

        binding.myTrainingsButton.setOnClickListener {
            viewModel.myAllTrainingParentScreen()
        }

        binding.aboutAppButton.setOnClickListener {
            viewModel.aboutAppScreen()
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

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}