package com.example.disputer.shop.domain.repo

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.disputer.core.Resource
import com.example.disputer.shop.data.Shop

interface ShopDataSource {
    suspend fun getShops() : Resource<List<Shop>>
    suspend fun addShop(shop: Shop) : Resource<Unit>
    suspend fun deleteShop(shop: Shop) : Resource<Unit>
    fun observeShopsLiveData(): LiveData<Resource<List<Shop>>>
}