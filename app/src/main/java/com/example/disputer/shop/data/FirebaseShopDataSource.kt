package com.example.disputer.shop.data

import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.disputer.core.Resource
import com.example.disputer.shop.domain.repo.ShopDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class FirebaseShopDataSource(
    private val firestore: FirebaseFirestore
) : ShopDataSource {

    private companion object {
        const val SHOPS_COLLECTION = "shop"
    }

    override fun observeShopsLiveData(): LiveData<Resource<List<Shop>>> {
        return object : LiveData<Resource<List<Shop>>>() {
            private var listener: ListenerRegistration? = null

            override fun onActive() {
                super.onActive()
                listener = firestore.collection(SHOPS_COLLECTION)
                    .addSnapshotListener { snapshot, error ->
                        when {
                            error != null -> {
                                postValue(Resource.Error(error.message ?: "Unknown error"))
                            }
                            snapshot != null -> {
                                val shops = snapshot.documents.mapNotNull { document ->
                                    try {
                                        document.toObject(Shop::class.java)?.copy(id = document.id)
                                    } catch (e: Exception) {
                                        null
                                    }
                                }
                                postValue(Resource.Success(shops))
                            }
                        }
                    }
            }

            override fun onInactive() {
                super.onInactive()
                listener?.remove()
                listener = null
            }
        }
    }

    override suspend fun getShops(): Resource<List<Shop>> {
        return try {
            val querySnapshot = firestore.collection(SHOPS_COLLECTION)
                .get()
                .await()

            val shops = querySnapshot.documents.mapNotNull { document ->
                try {
                    document.toObject(Shop::class.java)?.copy(id = document.id)
                } catch (e: Exception) {
                    null
                }
            }

            Resource.Success(shops.filterNotNull())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load shops")
        }
    }

    override suspend fun addShop(shop: Shop): Resource<Unit> {
        return try {
            require(shop.imageUrl.isNotEmpty()) { "Image URL must not be empty" }
            require(shop.url.isNotEmpty()) { "Shop URL must not be empty" }

            val result = if (shop.id.isEmpty()) {
                // Добавление нового магазина
                firestore.collection(SHOPS_COLLECTION)
                    .add(shop)
                    .await()
            } else {
                // Обновление существующего
                firestore.collection(SHOPS_COLLECTION)
                    .document(shop.id)
                    .set(shop)
                    .await()
                null
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to save shop")
        }
    }

    override suspend fun deleteShop(shop: Shop): Resource<Unit> {
        return try {
            require(shop.id.isNotEmpty()) { "Shop ID must not be empty" }

            firestore.collection(SHOPS_COLLECTION)
                .document(shop.id)
                .delete()
                .await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to delete shop")
        }
    }
}