package com.example.disputer.coach.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.disputer.R
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ImageUtils
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentCoachProfileBinding

class CoachProfileInfoFragment : AbstractFragment<FragmentCoachProfileBinding>() {

    private lateinit var viewModel: CoachInfoViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCoachProfileBinding = FragmentCoachProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(CoachInfoViewModel::class.java)

        setUpViews()
    }

    private fun setUpViews() {
        val coach = viewModel.currentCoachLiveData().value

        coach?.let {
            binding.nameTextView.text = coach.name
            binding.emailTextView.text = coach.email
            binding.phoneTextView.text = coach.phoneNumber
            binding.telegramTextView.text = coach.telegram
            binding.qualificationTextView.text = coach.qualification

            if (coach.photoBase64.isNotEmpty()) {
                val bitmap = ImageUtils.base64ToBitmap(coach.photoBase64)
                if (bitmap != null) {
                    Glide.with(requireContext())
                        .load(bitmap)
                        .centerCrop()
                        .placeholder(R.drawable.user)
                        .into(binding.coachImageView)
                } else {
                    binding.coachImageView.setImageResource(R.drawable.user)
                }
            } else {
                binding.coachImageView.setImageResource(R.drawable.user)
            }

            binding.addressesContainer.removeAllViews()
            coach.address.forEach { address ->
                val addressTV = TextView(requireContext()).apply {
                    text = address
                    textSize = 16f
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                binding.addressesContainer.addView(addressTV)
            }
        }
    }
}