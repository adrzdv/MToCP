package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.document.PaginationResponseDto
import com.adrzdv.mtocp.data.model.dto.BranchDto
import com.adrzdv.mtocp.data.model.dto.DepartmentDto
import com.adrzdv.mtocp.data.model.dto.DepotDto
import com.adrzdv.mtocp.data.model.dto.DivisionDto
import com.adrzdv.mtocp.data.model.dto.RevisionTypeDto
import com.adrzdv.mtocp.data.model.dto.ViolationFullDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("/revision-types")
    suspend fun getRevisionTypes(): List<RevisionTypeDto>
    @GET("/departments")
    suspend fun getDepartments(): List<DepartmentDto>

    @GET("/divisions/all")
    suspend fun getDivisions(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 1000
    ): List<DivisionDto>

    @GET("/violations/full")
    suspend fun getViolationsFull(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("searchStr") searchStr: String? = null,
        @Query("onlyActive") onlyActive: Boolean? = null,
        @Query("typeIds") typeIds: List<Int>? = null,
        @Query("divisionIds") divisionIds: List<Int>? = null
    ): PaginationResponseDto<ViolationFullDto>

    @GET("/branches")
    suspend fun getBranches(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String? = "id",
        @Query("direction") direction: String? = "ASC"
    ): List<BranchDto>


    @GET("/depots")
    suspend fun getDepots(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String? = "id",
        @Query("direction") direction: String? = "ASC"
    ): List<DepotDto>
}