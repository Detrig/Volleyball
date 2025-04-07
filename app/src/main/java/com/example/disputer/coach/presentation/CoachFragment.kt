package com.example.disputer.coach.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.coach.data.Coach
import com.example.disputer.core.AbstractFragment
import com.example.disputer.databinding.FragmentCoachBinding

class CoachFragment : AbstractFragment<FragmentCoachBinding>() {

    private lateinit var adapter: CoachRcViewAdapter
    private val testCoachList = arrayListOf(
        Coach(
            "1",
            "АЛЕКСЕЙЦ",
            "911",
            "GANGSTA",
            "LOX@MAIL>RU",
            imageUrl = "https://img.freepik.com/free-photo/portrait-ghanaian-man_53876-32448.jpg?t=st=1743323571~exp=1743327171~hmac=9a2f4b0fff6e417c7a70789cec36699c97ffdc66c7b06057d2bf6fc45b850b62&w=740"
        ),
        Coach(
            "1",
            "ЮЛЯ ГЛАЗУНОВА СЕРГЕЕВНА",
            "8923125325432",
            "GANGSTA",
            "NELOX@MAIL>RU",
            imageUrl = "https://img.freepik.com/free-photo/beautiful-woman-body-lingerie_23-2149237795.jpg?t=st=1743323726~exp=1743327326~hmac=f2241a484b706970f58996fc5cf21dee1a6cae8b18352510f5f03079381026a0&w=740"
        )
    )

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentCoachBinding =
        FragmentCoachBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcView()
        adapter.update(testCoachList)
    }

    private fun initRcView() {
        adapter = CoachRcViewAdapter(object : CoachRcViewAdapter.OnCoachClickListener {
            override fun onClick(coach: Coach) {
                TODO("Not yet implemented")
            }

        })
        binding.coachRcView.adapter = adapter

    }
}