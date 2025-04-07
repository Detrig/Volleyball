package com.example.disputer.shop.domain.usecase

import com.example.disputer.core.Resource
import com.example.disputer.shop.data.Shop
import com.example.disputer.shop.domain.repo.ShopRepository

class GetShopsUseCase(private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(): Resource<List<Shop>> {
        return shopRepository.getShops()
    }
}