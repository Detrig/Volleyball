package com.example.disputer.training.presentation.training_coach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.databinding.FragmentTrainingCoachMainBinding

class TrainingCoachFragment : AbstractFragment<FragmentTrainingCoachMainBinding>() {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingCoachMainBinding =
        FragmentTrainingCoachMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}