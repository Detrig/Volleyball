package com.example.disputer.coach.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.coach.data.Coach
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentCoachBinding

class CoachFragment : AbstractFragment<FragmentCoachBinding>() {

    private lateinit var viewModel: CoachListViewModel
    private lateinit var adapter: CoachRcViewAdapter

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentCoachBinding =
        FragmentCoachBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(CoachListViewModel::class.java)
        viewModel.loadCoachs()
        initRcView()
        observeCoachListLiveData()
    }

    private fun initRcView() {
        adapter = CoachRcViewAdapter(object : CoachRcViewAdapter.OnCoachClickListener {
            override fun onClick(coach: Coach) {
                viewModel.coachProfileScreen(coach)
            }

        })
        binding.coachRcView.adapter = adapter
    }

    private fun observeCoachListLiveData() {
        val coachLiveData = viewModel.coachListLiveData()
        coachLiveData.value?.let {
            adapter.update(ArrayList(it))
        }

        coachLiveData.observe(viewLifecycleOwner) {
            adapter.update(ArrayList(it))
        }
    }
}