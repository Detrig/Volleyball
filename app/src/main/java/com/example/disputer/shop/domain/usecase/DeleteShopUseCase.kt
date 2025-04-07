package com.example.disputer.shop.domain.usecase

import com.example.disputer.core.Resource
import com.example.disputer.shop.data.Shop
import com.example.disputer.shop.domain.repo.ShopRepository

class DeleteShopUseCase(private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(shop: Shop): Resource<Unit> {
        return shopRepository.deleteShop(shop)
    }
}