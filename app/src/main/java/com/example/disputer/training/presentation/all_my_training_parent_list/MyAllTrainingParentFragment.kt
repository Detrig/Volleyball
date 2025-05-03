package com.example.disputer.training.presentation.all_my_training_parent_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentMyAllTrainingParentBinding
import com.example.disputer.training.data.Training
import com.example.disputer.training.presentation.training_parent.TrainingsRecyclerViewAdapter

class MyAllTrainingParentFragment : AbstractFragment<FragmentMyAllTrainingParentBinding>() {

    private lateinit var viewModel: MyAllTrainingParentViewModel
    private lateinit var adapter: TrainingsRecyclerViewAdapter

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyAllTrainingParentBinding =
        FragmentMyAllTrainingParentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(MyAllTrainingParentViewModel::class.java)

        viewModel.getYourChildrenAllTrainings()
        initRcView()

        viewModel.yourChildrenTrainingLiveLiveData().value?.let {
            adapter.update(ArrayList(it))
        }
        setUpObservers()
    }

    private fun initRcView() {
        adapter = TrainingsRecyclerViewAdapter(object : TrainingsRecyclerViewAdapter.OnTrainingClickListener {
            override fun onClick(training: Training) {

            }
        })
        binding.allTrainingRcView.adapter = adapter
    }

    private fun setUpObservers() {
        viewModel.yourChildrenTrainingLiveLiveData().observe(viewLifecycleOwner) {
            adapter.update(ArrayList(it))
        }
    }
}