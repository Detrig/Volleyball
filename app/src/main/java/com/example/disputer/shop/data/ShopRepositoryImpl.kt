package com.example.disputer.shop.data

import android.net.Uri
import androidx.lifecycle.LiveData
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

    override fun observeShopsLiveData(): LiveData<Resource<List<Shop>>> =
        shopDataSource.observeShopsLiveData()
}