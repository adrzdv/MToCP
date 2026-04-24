package com.adrzdv.mtocp.data.api

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.concurrent.TimeUnit

object UpdateManager {
    private const val DOWNLOAD_TIMEOUT_SECONDS = 120L

    suspend fun downloadApk(url: String, targetFile: File): Boolean = withContext(Dispatchers.IO) {
        var result = false

        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(DOWNLOAD_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DOWNLOAD_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DOWNLOAD_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()

            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                Log.d("UPDATE_HANDLER", "Код ответа: ${response.code}")

                if (!response.isSuccessful) {
                    result = false
                } else {
                    val body = response.body
                    body.byteStream().use { input ->
                        targetFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    result = true
                }
            }


        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("UPDATE_HANDLER", "Ошибка: ${e.message}")
            e.printStackTrace()
            result = false
        }

        return@withContext result
    }

    fun installApk(context: Context, apkFile: File) {

        val contentUri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            apkFile
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
