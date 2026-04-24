package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.document.DocumentRequestDto
import com.adrzdv.mtocp.data.model.document.DocumentResponseDto
import com.adrzdv.mtocp.data.model.document.DocumentUserDataDto
import com.adrzdv.mtocp.data.model.document.PaginationResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DocRequestApi {

    @POST("documents")
    suspend fun createDocument(
        @Body request: DocumentRequestDto
    ): DocumentResponseDto

    @GET("documents")
    suspend fun getAll(): List<DocumentResponseDto>

    @GET("documents/full")
    suspend fun getFull(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("owner") owner: String?,
        @Query("prefix") prefix: String?
    ): PaginationResponseDto<DocumentUserDataDto>
}