package com.example.disputer.shop.domain.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.disputer.core.Resource
import java.io.ByteArrayOutputStream

class ImageProcessor(private val context: Context) {

    suspend fun prepareImage(uri: Uri, maxSizeBytes: Int): Resource<ByteArray> {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: return Resource.Error("Cannot open image")

            // Декодируем с уменьшением размера
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream.close()

            // Рассчитываем коэффициент сжатия
            options.inSampleSize = calculateInSampleSize(options, maxSizeBytes)
            options.inJustDecodeBounds = false

            val bitmap = context.contentResolver.openInputStream(uri)?.use {
                BitmapFactory.decodeStream(it, null, options)
            } ?: return Resource.Error("Failed to decode image")

            // Конвертируем в JPG с компрессией
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
            val bytes = outputStream.toByteArray()

            if (bytes.size > maxSizeBytes) {
                return Resource.Error("Compressed image still too large")
            }

            Resource.Success(bytes)
        } catch (e: Exception) {
            Resource.Error("Image processing failed: ${e.message}")
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, maxSizeBytes: Int): Int {
        val (width, height) = options.outWidth to options.outHeight
        var inSampleSize = 1

        while (width * height * 4 / (inSampleSize * inSampleSize) > maxSizeBytes) {
            inSampleSize *= 2
        }

        return inSampleSize
    }
}