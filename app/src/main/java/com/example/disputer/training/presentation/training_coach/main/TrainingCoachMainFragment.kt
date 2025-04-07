package com.example.disputer.training.presentation.training_coach.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disputer.shop.data.Shop
import com.example.disputer.authentication.presentation.main.MainActivity
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentTrainingCoachMainBinding
import com.example.disputer.training.data.Training
import com.example.disputer.training.presentation.training_coach.TrainingCoachViewModel
import com.example.disputer.training.presentation.training_parent.ShopsRcViewAdapter
import com.example.disputer.training.presentation.training_parent.TrainingsRecyclerViewAdapter

class TrainingCoachMainFragment : AbstractFragment<FragmentTrainingCoachMainBinding>() {

    private lateinit var trainingAdapter: TrainingsRecyclerViewAdapter
    private lateinit var shopAdapter: ShopsRcViewAdapter
    private lateinit var viewModel: TrainingCoachViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingCoachMainBinding =
        FragmentTrainingCoachMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).showHeaderBottomNav()

        viewModel = (activity as ProvideViewModel).viewModel(TrainingCoachViewModel::class.java)
        initRcView()

        loadRcViewLists()

        viewModel.trainingsLiveData().observe(viewLifecycleOwner) { trainings ->
            trainings?.let {
                trainingAdapter.update(ArrayList(trainings))
            }
        }

        viewModel.shopsLiveData().observe(viewLifecycleOwner) { shops ->
            shops?.let {
                shopAdapter.update(ArrayList(shops))
            }
        }

        binding.addTraining.setOnClickListener {
            viewModel.addTrainingScreen()
        }

        binding.addShop.setOnClickListener {
            viewModel.addShopScreen()
        }


    }

    private fun initRcView() {
        trainingAdapter = TrainingsRecyclerViewAdapter(object :
            TrainingsRecyclerViewAdapter.OnTrainingClickListener {
            override fun onClick(training: Training) {
                Toast.makeText(requireContext(), training.coachName, Toast.LENGTH_SHORT).show()
            }

        })
        binding.rcViewTrainings.adapter = trainingAdapter

        shopAdapter = ShopsRcViewAdapter(object : ShopsRcViewAdapter.OnShopClickListener {
            override fun onClick(shop: Shop) {
                openUrl(shop.url)
            }
        })
        binding.rcViewShops.adapter = shopAdapter
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка при открытии ссылки: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadRcViewLists() {
        viewModel.shopsLiveData().value?.let {
            shopAdapter.update(ArrayList(it))
        }

        viewModel.trainingsLiveData().value?.let {
            trainingAdapter.update(ArrayList(it))
        }
    }
}