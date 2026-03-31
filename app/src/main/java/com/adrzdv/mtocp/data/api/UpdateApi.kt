package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.UpdateConfig
import retrofit2.Response
import retrofit2.http.GET

interface UpdateApi {
    @GET("version.json")
    suspend fun getUpdateInfo(): Response<UpdateConfig>
}