package com.example.disputer.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.databinding.FragmentInformationBinding

class InfoFragment : AbstractFragment<FragmentInformationBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentInformationBinding =
        FragmentInformationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}