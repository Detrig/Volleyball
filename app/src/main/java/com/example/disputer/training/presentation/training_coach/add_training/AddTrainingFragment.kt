package com.example.disputer.training.presentation.training_coach.add_training

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentAddTrainingBinding
import com.example.disputer.training.data.Training
import com.example.disputer.training.presentation.training_coach.TrainingCoachViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTrainingFragment : AbstractFragment<FragmentAddTrainingBinding>() {

    private lateinit var viewModel : TrainingCoachViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddTrainingBinding =
        FragmentAddTrainingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(TrainingCoachViewModel::class.java)
        dateAndTimePickers()

        binding.saveButton.setOnClickListener {
            val training = Training(
                title = binding.titleEditText.text.toString(),
                description = binding.descriptionEditText.text.toString(),
                date = binding.dateEditText.text.toString(),
                time = binding.timeEditText.text.toString(),
                maxPersonCount = binding.maxPersonEditText.text.toString().toIntOrNull() ?: 30,
                address = binding.addressEditText.text.toString(),
                addressInfo = binding.addressInfoEditText.text.toString(),
                group = binding.groupEditText.text.toString(),
                birthYear = binding.birthYearEditText.text.toString()
            )

            viewModel.addTraining(training)
        }

        viewModel.addTrainingUiStateLiveData().observe(viewLifecycleOwner) { uiState ->
            uiState.update(binding)
        }

    }

    private fun dateAndTimePickers() {
        binding.dateEditText.setOnClickListener {
            showDatePicker()
        }

        binding.timeEditText.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(selectedDate))
            binding.dateEditText.setText(dateString)
        }

        datePicker.show(parentFragmentManager, "DATE_PICKER_TAG")
    }

    private fun showTimePicker() {
        val currentTime = binding.timeEditText.text.toString()
        val calendar = Calendar.getInstance()

        currentTime.takeIf { it.isNotEmpty() }?.let {
            val parts = it.split(":")
            if (parts.size == 2) {
                calendar.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
                calendar.set(Calendar.MINUTE, parts[1].toInt())
            }
        }

        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val selectedTime = "%02d:%02d".format(hourOfDay, minute)
                binding.timeEditText.setText(selectedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )

        timePicker.show()
    }

}