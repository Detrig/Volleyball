package com.example.disputer.training.presentation.training_sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.children.presentation.ChildrenSingUpRcViewAdapter
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentSignUpTrainingBinding
import com.example.disputer.training.data.Training

class TrainingSignUpFragment : AbstractFragment<FragmentSignUpTrainingBinding>() {

    private lateinit var viewModel: TrainingSignUpViewModel
    private lateinit var childrenRcViewAdapter: ChildrenSingUpRcViewAdapter
    private lateinit var currentTraining: Training

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpTrainingBinding =
        FragmentSignUpTrainingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(TrainingSignUpViewModel::class.java)

        getCurrentTraining()
        viewModel.loadChildren(currentTraining.id)

        val alreadySignedUpIds = viewModel.signedUpForTrainingChildrensLiveData().value
        alreadySignedUpIds?.let {
            initRcView(alreadySignedUpIds.map { it.uid }.toSet())
        }

        signUpListener()
    }

    private fun initRcView(alreadySignedUpIds: Set<String>) {
        childrenRcViewAdapter = ChildrenSingUpRcViewAdapter(
            onSelectionChanged = { selectedChildren ->
                binding.signUpButton.isEnabled = selectedChildren.isNotEmpty()
            },
            alreadySignedUpIds = alreadySignedUpIds
        )
        binding.childrenRecyclerView.adapter = childrenRcViewAdapter
    }

    private fun signUpListener() {
        binding.signUpButton.setOnClickListener {
            val selectedChildren = childrenRcViewAdapter.getSelectedChildren().toList()
            if (selectedChildren.isNotEmpty()) {
                viewModel.signUpChildren(currentTraining.id, selectedChildren)
            }
        }
    }

    private fun getCurrentTraining() {
        currentTraining = viewModel.clickedTrainingLiveData().value ?: Training()

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
}