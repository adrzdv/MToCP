package com.adrzdv.mtocp.domain.usecase

import android.util.Log
import com.adrzdv.mtocp.util.DirectoryHandler
import java.io.File
import java.util.Locale

class DeleteViolationPhotoUseCase {
    fun invoke(code: Int, orderNumb: String, coachNumber: String) {
        val path = DirectoryHandler.MEDIA_DIRECTORY + "/" + orderNumb + "/" + coachNumber
        val dir = File(path)
        val files = dir.listFiles()

        if (files != null) {
            for (file in files) {
                if (file.name.lowercase(Locale.ROOT).contains("img_$code")) {
                    file.delete()
                    Log.d("DELETE-VIOLATION", "Photo deleted successfully")
                }
            }
        }
        Log.d("DELETE-VIOLATION", "Cant delete photo")
    }
}