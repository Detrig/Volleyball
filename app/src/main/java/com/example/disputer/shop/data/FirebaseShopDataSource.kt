package com.example.disputer.shop.data

import android.util.Base64
import com.example.disputer.core.Resource
import com.example.disputer.shop.domain.repo.ShopDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseShopDataSource(
    private val firestore: FirebaseFirestore
) : ShopDataSource {

    private companion object {
        const val SHOPS_COLLECTION = "shop"
        const val STORAGE_SHOP_IMAGES_PATH = "shop_images/"
        const val MAX_IMAGE_SIZE = 990_000
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

    override suspend fun uploadShopImage(imageBytes: ByteArray): Resource<String> {
        return try {
            if (imageBytes.size > MAX_IMAGE_SIZE) {
                return Resource.Error("Image too large (max ${MAX_IMAGE_SIZE / 1024}KB)")
            }
            val base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            Resource.Success(base64)
        } catch (e: Exception) {
            Resource.Error("Image encoding failed: ${e.message}")
        }
    }
}