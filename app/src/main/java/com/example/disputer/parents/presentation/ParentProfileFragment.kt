package com.example.disputer.parents.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.authentication.data.Student
import com.example.disputer.authentication.data.Training
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentParentProfileBinding
import com.example.disputer.parents.presentation.adapter.ChildrenRcViewAdapter
import com.example.disputer.training.presentation.main.TrainingsRecyclerViewAdapter

class ParentProfileFragment : AbstractFragment<FragmentParentProfileBinding>() {

    private lateinit var parentViewModel: ParentViewModel
    private lateinit var childrenAdapter: ChildrenRcViewAdapter
    private lateinit var trainingsAdapter: TrainingsRecyclerViewAdapter

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentParentProfileBinding =
        FragmentParentProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentViewModel = (activity as ProvideViewModel).viewModel(ParentViewModel::class.java)

        parentViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            uiState
        }

        initRcView()
    }

    private fun initRcView() {
        childrenAdapter =
            ChildrenRcViewAdapter(object : ChildrenRcViewAdapter.OnChildClickListener {
                override fun onClick(student: Student) {
                    TODO("Not yet implemented")
                }
            })
        binding.childrenRcView.adapter = childrenAdapter

        trainingsAdapter = TrainingsRecyclerViewAdapter(object :
            TrainingsRecyclerViewAdapter.OnTrainingClickListener {
            override fun onClick(training: Training) {
                TODO("Not yet implemented")
            }
        })
        binding.childrenRcView.adapter = trainingsAdapter
    }
}