package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.LogEntry
import com.adrzdv.mtocp.data.model.NameRequest
import com.adrzdv.mtocp.data.model.NumberResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DocRequestApi {

    @POST("getnumber")
    suspend fun getNumber(@Body request: NameRequest): NumberResponse

    @POST("lastlogs")
    suspend fun getLastLogs(
        @Body request: Map<String, String> = emptyMap()
    ): List<LogEntry>
}