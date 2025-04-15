package com.example.disputer.training.presentation.training_coach.add_training

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.disputer.children.data.Student
import com.example.disputer.children.presentation.list.ChildrenRcViewAdapter
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

    private lateinit var viewModel: TrainingCoachViewModel
    private lateinit var currentTraining: Training
    private lateinit var childrenAdapter: ChildrenRcViewAdapter

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddTrainingBinding =
        FragmentAddTrainingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(TrainingCoachViewModel::class.java)
        getCurrentTraining()
        setUpRcView()
        dateAndTimePickers()
        setupAddressSelection()


        //Если id есть то обновялется существующее значение
        binding.saveButton.setOnClickListener {
            var training = Training()
            if (currentTraining.id == "") {
                training = Training(
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
            } else {
                training = Training(
                    id = currentTraining.id,
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
            }

            viewModel.addTraining(training)
        }

        binding.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        viewModel.addTrainingUiStateLiveData().observe(viewLifecycleOwner) { uiState ->
            uiState.update(binding)
        }

    }

    private fun getCurrentTraining() {
        currentTraining = viewModel.clickedTrainingLiveData().value ?: Training()

        if (currentTraining.id.isNotEmpty()) binding.deleteButton.visibility = View.VISIBLE

        currentTraining.let { training ->
            with(binding) {
                titleEditText.setText(training.title)
                descriptionEditText.setText(training.description)
                dateEditText.setText(training.date)
                timeEditText.setText(training.time)
                addressEditText.setText(training.address)
                addressInfoEditText.setText(training.addressInfo)
                groupEditText.setText(training.group)
                birthYearEditText.setText(training.birthYear)
                maxPersonEditText.setText(training.maxPersonCount.toString())
            }
        }
    }

    private fun setUpRcView() {
        viewModel.getChildrenSignedUpForTraining(currentTraining.id)
        childrenAdapter = ChildrenRcViewAdapter(object : ChildrenRcViewAdapter.OnChildrenClickListener {
            override fun onClick(student: Student) {}
        })
        binding.signedUpChildrensRcView.adapter = childrenAdapter

        viewModel.signedUpForTrainingChildrensByParentLiveData().observe(viewLifecycleOwner) {
            childrenAdapter.update(ArrayList(it))
            binding.signedUpChildrenTV.text = "${it.size} / ${currentTraining.maxPersonCount}"
        }

    }

    private fun setupAddressSelection() {
        val addresses = viewModel.getCoachAddresses()

        // Если есть сохраненные адреса, показываем выпадающий список
        if (addresses.isNotEmpty()) {
            setupAddressAutoComplete(addresses)
        }
    }

    private fun setupAddressAutoComplete(addresses: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            addresses + "Другой адрес"
        )

        (binding.addressInputLayout.editText as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, position, _ ->
                if (position == addresses.size) {
                    binding.addressEditText.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    binding.addressEditText.setText("")
                } else {
                    binding.addressEditText.inputType = InputType.TYPE_NULL
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление тренировки")
            .setMessage("Вы уверены, что хотите удалить эту тренировку?")
            .setPositiveButton("Удалить") { _, _ ->
                viewModel.deleteTraining(currentTraining)
            }
            .setNegativeButton("Отмена", null)
            .create()
            .show()
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearClickedTrainingLiveData()
    }
}