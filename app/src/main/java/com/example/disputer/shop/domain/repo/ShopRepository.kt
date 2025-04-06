package com.example.disputer.shop.domain.repo

import android.net.Uri
import com.example.disputer.core.Resource
import com.example.disputer.shop.data.Shop

interface ShopRepository {

    suspend fun getShops() : Resource<List<Shop>>
    suspend fun addShop(shop: Shop) : Resource<Unit>
    suspend fun deleteShop(shop: Shop) : Resource<Unit>
    suspend fun uploadShopImage(imageBytes: ByteArray) : Resource<String>
}