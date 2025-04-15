package com.example.disputer.training.presentation.training_sign_off

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disputer.children.presentation.ChildrenSingUpRcViewAdapter
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentSignOffTrainingBinding
import com.example.disputer.training.data.Training
import com.example.disputer.training.presentation.training_sign_up.TrainingSignUpViewModel

class TrainingSignOffFragment : AbstractFragment<FragmentSignOffTrainingBinding>() {

    private lateinit var viewModel: TrainingSignUpViewModel
    private lateinit var childrenRcViewAdapter: ChildrenSingUpRcViewAdapter
    private lateinit var currentTraining: Training

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignOffTrainingBinding =
        FragmentSignOffTrainingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(TrainingSignUpViewModel::class.java)

        getCurrentTraining()
        initRcView()
        setUpObservers()
        setUpClickListeners()
        viewModel.loadChildren(currentTraining.id)
    }

    private fun setUpClickListeners() {
        binding.signOffButton.setOnClickListener {
            val selectedChildrens = childrenRcViewAdapter.getSelectedChildren().toList()
            Log.d("VB-17", "signOff selectedChildrens: ${selectedChildrens.map {it.uid}}")
            if (selectedChildrens.isNotEmpty()) {
                viewModel.signOffChildren(currentTraining.id, selectedChildrens)
            } else {
                Toast.makeText(requireContext(), "Выберите ребенка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRcView() {
        childrenRcViewAdapter = ChildrenSingUpRcViewAdapter(
            onSelectionChanged = { selectedChildren ->

            }
        )
        binding.childrenRecyclerView.adapter = childrenRcViewAdapter
    }

    private fun setUpObservers() {
        viewModel.signedUpForTrainingChildrensLiveData().observe(viewLifecycleOwner) { signedUpChildren ->
            childrenRcViewAdapter.update(signedUpChildren, signedUpChildren.map { it.uid}.toSet())
        }

        viewModel.signUpMessageLiveData.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentTraining() {
        currentTraining = viewModel.clickedTrainingToSignUpLiveData().value ?: Training()
        currentTraining.let { training ->
            with(binding) {
                titleTextView.text = training.title
                descriptionTextView.text = training.description
                dateTextView.text = training.date
                timeTextView.text = training.time
                addressTextView.text = training.address
                addressInfoTextView.text = training.addressInfo
                groupTextView.text = training.group
                birthYearTextView.text = training.birthYear
                participantsCountTextView.text = training.maxPersonCount.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearClickedTrainingToSignUpLiveData()
        viewModel.clearMessageLiveData()
    }
}