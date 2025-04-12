package com.example.disputer.coach.presentation.edit_profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.disputer.coach.data.Coach
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentEditCoachProfileBinding

class EditCoachProfileFragment : AbstractFragment<FragmentEditCoachProfileBinding>() {

    private lateinit var viewModel: EditCoachProfileViewModel
    private val addressEditTexts = mutableListOf<EditText>()

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditCoachProfileBinding =
        FragmentEditCoachProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(EditCoachProfileViewModel::class.java)
        val currentCoach = viewModel.currentCoach()
        Log.d("VB-04", currentCoach.toString())

        setupViews(currentCoach)
        getAddressesFromEditTexts()
        setClickListeners(currentCoach!!)
    }

    private fun setClickListeners(currentCoach: Coach) {
        binding.addAddressButton.setOnClickListener {
            addAddressEditText("")
        }

        binding.saveButton.setOnClickListener {
            val coach = Coach(
                uid =  currentCoach.uid,
                email = currentCoach.email,
                name = binding.nameEditText.text.toString(),
                phoneNumber = binding.phoneEditText.text.toString(),
                qualification = binding.qualificationEditText.text.toString(),
                telegram = binding.telegramEditText.text.toString(),
                address = getAddressesFromEditTexts()
                )
            viewModel.updateCoach(coach)
        }
    }

    private fun setupViews(coach: Coach?) {
        coach?.let {
            binding.emailEditText.setText(coach.email)
            binding.nameEditText.setText(coach.name)
            binding.phoneEditText.setText(coach.phoneNumber)
            binding.telegramEditText.setText(coach.phoneNumber)
            binding.qualificationEditText.setText(coach.qualification)

            val address = coach.address
            address.forEach {
                addAddressEditText(it)
            }
        }
    }

    private fun addAddressEditText(initialText: String) {
        val editText = EditText(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16.dpToPx(), 0, 0)
            }
            hint = "Введите адрес тренировки"
            setText(initialText)
        }

        binding.addressesContainer.addView(editText)
        addressEditTexts.add(editText)
    }

    private fun getAddressesFromEditTexts(): List<String> {
        return addressEditTexts
            .map { it.text.toString() }
            .filter { it.isNotBlank() }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}