package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.LogEntry
import com.adrzdv.mtocp.data.model.old.NameRequest
import com.adrzdv.mtocp.data.model.NumberResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DocRequestApi {

    @POST("getnumber")
    suspend fun getNumber(@Body request: NameRequest): NumberResponse

    @GET("gottennumbers")
    suspend fun getLastLogs(): List<LogEntry>
}