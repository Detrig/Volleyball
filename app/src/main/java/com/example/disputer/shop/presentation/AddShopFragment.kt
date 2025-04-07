package com.example.disputer.shop.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentAddShopBinding
import com.example.disputer.shop.data.Shop

class AddShopFragment : AbstractFragment<FragmentAddShopBinding>() {

    private lateinit var viewModel : ShopViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddShopBinding =
        FragmentAddShopBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(ShopViewModel::class.java)


        binding.saveButton.setOnClickListener {
            val shop = Shop(
                url = binding.urlEditText.text.toString(),
                imageUrl = binding.imageUrlEditText.text.toString()
            )
            viewModel.addShop(shop)
        }
    }
}