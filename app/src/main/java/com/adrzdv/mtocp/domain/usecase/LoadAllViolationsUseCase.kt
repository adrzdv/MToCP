package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.data.api.DictionaryApi
import com.adrzdv.mtocp.data.model.dto.ViolationFullDto

class LoadAllViolationsUseCase(private val api: DictionaryApi) {
    val dictionaryApi: DictionaryApi
        get() = api

    suspend fun invoke(): List<ViolationFullDto> {
        val result = mutableListOf<ViolationFullDto>()
        var page = 0
        val size = 100

        while (true) {
            val response = api.getViolationsFull(page, size)

            result.addAll(response.entities)

            if (result.size >= response.total) break

            page++
        }

        return result
    }
}
