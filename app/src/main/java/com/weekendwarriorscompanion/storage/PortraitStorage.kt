package com.weekendwarriorscompanion.storage

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class PortraitStorage(private val context: Context) {
    fun savePortrait(bitmap: Bitmap): String {
        val fileName = "portrait_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
        }
        return file.absolutePath
    }

    fun deletePortrait(path: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }
}
