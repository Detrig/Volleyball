package com.example.disputer.training.presentation.training_sign_up

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        initRcView()
        setUpObservers()
        signUpListener()
    }

    private fun initRcView() {
        childrenRcViewAdapter = ChildrenSingUpRcViewAdapter(
            onSelectionChanged = { selectedChildren ->
                binding.signUpButton.isEnabled = selectedChildren.isNotEmpty()
            }
        )
        binding.childrenRecyclerView.adapter = childrenRcViewAdapter
    }

    private fun setUpObservers() {
//        viewModel.currentParentChildrenListLiveData().observe(viewLifecycleOwner) { allChildren ->
//            childrenRcViewAdapter.update(allChildren)
//        }

        viewModel.signedUpForTrainingChildrensLiveData().observe(viewLifecycleOwner) { signedUpChildren ->
            val allChildren = viewModel.currentParentChildrenListLiveData().value ?: emptyList()
            childrenRcViewAdapter.update(allChildren, signedUpChildren.map { it.uid}.toSet())
        }

        viewModel.signUpMessageLiveData.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpListener() {
        binding.signUpButton.setOnClickListener {
            val selectedChildrens = childrenRcViewAdapter.getSelectedChildren().toList()
            if (selectedChildrens.isNotEmpty()) {
                viewModel.signUpChildren(currentTraining.id, selectedChildrens)
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