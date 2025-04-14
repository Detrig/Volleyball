package com.example.disputer.schedule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.training.data.Training
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentScheduleBinding
import com.example.disputer.training.presentation.training_parent.TrainingsRecyclerViewAdapter

class ScheduleFragment : AbstractFragment<FragmentScheduleBinding>() {

    private lateinit var adapter : TrainingsRecyclerViewAdapter
    private lateinit var viewModel: ScheduleViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleBinding =
        FragmentScheduleBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(ScheduleViewModel::class.java)
        
        initRcView()

        viewModel.getTrainings()
        viewModel.futureTrainingsLiveData().observe(viewLifecycleOwner) {
            adapter.update(ArrayList(it))
        }
    }

    private fun initRcView() {
        adapter = TrainingsRecyclerViewAdapter(object : TrainingsRecyclerViewAdapter.OnTrainingClickListener {
            override fun onClick(training: Training) {
                viewModel.trainingSignUpScreen(training)
            }
        })
        binding.rcViewSchedule.adapter = adapter
    }
}