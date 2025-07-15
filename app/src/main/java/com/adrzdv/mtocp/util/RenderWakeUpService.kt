package com.adrzdv.mtocp.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.use
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class RenderWakeUpService {
    suspend fun wakeUpRender(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://reg-log-bt.onrender.com")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"

            conn.connectTimeout = 5000
            conn.readTimeout = 2 * 60 * 1000

            conn.doOutput = true
            conn.outputStream.use {
                it.write("""{"ping":true}""".toByteArray())
            }

            val responseCode = conn.responseCode
            conn.disconnect()

            responseCode in 200..299 || responseCode == 404
        } catch (e: SocketTimeoutException) {
            println("🔴 Render не ответил за 2 минуты: ${e.message}")
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getNumber(fullName: String): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://reg-log-bt.onrender.com/getnumber")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.connectTimeout = 15_000
            conn.readTimeout = 15_000
            conn.doOutput = true

            val body = """{"name":"$fullName"}"""
            conn.outputStream.use {
                it.write(body.toByteArray())
            }

            val responseCode = conn.responseCode
            if (responseCode in 200..299) {
                val json = conn.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(json)
                jsonObject.getString("number")
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}