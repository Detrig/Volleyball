package com.example.disputer.shop.data

import android.net.Uri
import com.example.disputer.core.Resource
import com.example.disputer.shop.domain.repo.ShopDataSource
import com.example.disputer.shop.domain.repo.ShopRepository

class ShopRepositoryImpl(
    private val shopDataSource: ShopDataSource
) : ShopRepository {

    override suspend fun getShops(): Resource<List<Shop>> =
        shopDataSource.getShops()

    override suspend fun addShop(shop: Shop): Resource<Unit> =
        shopDataSource.addShop(shop)

    override suspend fun deleteShop(shop: Shop): Resource<Unit> =
        shopDataSource.deleteShop(shop)

    override suspend fun uploadShopImage(imageBytes: ByteArray): Resource<String> =
        shopDataSource.uploadShopImage(imageBytes)
}