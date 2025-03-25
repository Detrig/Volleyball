package com.example.disputer.training.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.authentication.data.Training
import com.example.disputer.authentication.presentation.main.MainActivity
import com.example.disputer.core.AbstractFragment
import com.example.disputer.databinding.FragmentTrainingMainBinding

class TrainingMainFragment : AbstractFragment<FragmentTrainingMainBinding>() {

    private lateinit var adapter : TrainingsRecyclerViewAdapter
    private val trainingList = arrayListOf(
        Training(
            id = "1",
            time = "17:00",
            date = "Пн, 5 июня",
            addressInfo = "Спортивный комплекс 'Триумф', зал волейбола",
            address = "ул. Хабарова, 27, г. Якутск"
        ),
        Training(
            id = "2",
            time = "19:30",
            date = "Вт, 6 июня",
            addressInfo = "СК '50 лет Победы', основной зал",
            address = "ул. Петра Алексеева, 6, г. Якутск"
        ),
        Training(
            id = "3",
            time = "18:00",
            date = "Ср, 7 июня",
            addressInfo = "СВФУ им. М.К. Аммосова, спортивный зал КФЕН",
            address = "ул. Кулаковского, 48, г. Якутск"
        ),
        Training(
            id = "4",
            time = "16:00",
            date = "Чт, 8 июня",
            addressInfo = "Дворец спорта 'Эллэй Боотур'",
            address = "ул. Лермонтова, 60/2, г. Якутск"
        ),
        Training(
            id = "5",
            time = "20:00",
            date = "Пт, 9 июня",
            addressInfo = "Школа №5, спортивный зал",
            address = "ул. Каландарашвили, 15, г. Якутск"
        ),
        Training(
            id = "6",
            time = "15:30",
            date = "Сб, 10 июня",
            addressInfo = "ФОК 'Модун'",
            address = "ул. Петра Алексеева, 205/1, г. Якутск"
        ),
        Training(
            id = "7",
            time = "14:00",
            date = "Вс, 11 июня",
            addressInfo = "Спортзал ЯГСХА",
            address = "ул. Красильникова, 15, г. Якутск"
        )
    )
    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingMainBinding =
        FragmentTrainingMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).showHeaderBottomNav()

        initRcView()
        adapter.update(trainingList)

    }

    private fun initRcView() {
        adapter = TrainingsRecyclerViewAdapter(object : TrainingsRecyclerViewAdapter.OnTrainingClickListener {
            override fun onClick(training: Training) {
                TODO("Not yet implemented")
            }

        })
        binding.rcViewTrainings.adapter = adapter

    }
}