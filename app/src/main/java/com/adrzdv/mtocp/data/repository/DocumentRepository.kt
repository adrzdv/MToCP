package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.api.DocRequestApi
import com.adrzdv.mtocp.data.model.document.DocumentRequestDto
import com.adrzdv.mtocp.data.model.document.DocumentUserDataDto
import com.adrzdv.mtocp.data.model.document.PaginationResponseDto

class DocumentRepository(
    private val api: DocRequestApi,
    private val userDataStorage: UserDataStorage
) {

    suspend fun createDocument(username: String): String {
        val response = api.createDocument(
            DocumentRequestDto(
                username = username,
                userId = userDataStorage.getUserId(),
                prefix = userDataStorage.getUserBranch() ?: ""
            )
        )
        return response.fullNumber
    }

    suspend fun getDocumentsPage(
        page: Int,
        size: Int,
        owner: String? = null,
        prefix: String? = null
    ): PaginationResponseDto<DocumentUserDataDto> {
        return api.getFull(page, size, owner, prefix)
    }
}