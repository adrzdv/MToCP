package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.BranchDao
import com.adrzdv.mtocp.data.db.dao.DepotDao
import com.adrzdv.mtocp.data.db.entity.BranchEntity
import com.adrzdv.mtocp.data.db.entity.DepotEntity
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.repository.DepotRepository
import kotlinx.coroutines.flow.Flow

class DepotRepositoryImpl(
    private val depotDao: DepotDao,
    private val branchDao: BranchDao
): DepotRepository {

    val allDepotsWithBranches: Flow<List<DepotWithBranch>> =
        depotDao.getAllDepotsWithBranches()

    val allBranches: Flow<List<BranchEntity>> =
        branchDao.getAllBranches()

    fun searchDepots(query: String): Flow<List<DepotWithBranch>> =
        depotDao.searchDepots(query)

    fun getDepotsByBranch(branchId: Int): Flow<List<DepotWithBranch>> =
        depotDao.getDepotsByBranch(branchId)

    override suspend fun getAll(): List<DepotWithBranch> = depotDao.getAllDepotsWithBranchesList()

    override suspend fun getEntity(id: Int): DepotWithBranch {
        return depotDao.getDepotById(id)
            ?: throw IllegalArgumentException("Depot with id=$id not found")
    }

    override suspend fun saveAll(entities: List<DepotWithBranch>) {
        val branches = entities.map { it.branch }.distinctBy { it.id }
        val depots = entities.map { it.depot }

        branchDao.insertBranches(branches)
        depotDao.insertDepots(depots)
    }

    suspend fun syncWithServer(branches: List<BranchEntity>, depots: List<DepotEntity>) {
        branchDao.insertBranches(branches)
        depotDao.insertDepots(depots)
    }

    suspend fun updateDepot(depot: DepotEntity) = depotDao.updateDepot(depot)

    suspend fun deleteDepot(id: Int) = depotDao.softDeleteDepot(id)
}
