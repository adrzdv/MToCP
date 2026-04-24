package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.BranchDao
import com.adrzdv.mtocp.data.db.dao.DepartmentDao
import com.adrzdv.mtocp.data.db.dao.DepotDao
import com.adrzdv.mtocp.data.db.dao.DivisionDao
import com.adrzdv.mtocp.data.db.dao.RevisionTypeDao
import com.adrzdv.mtocp.data.db.entity.BranchEntity
import com.adrzdv.mtocp.data.db.entity.DepartmentEntity
import com.adrzdv.mtocp.data.db.entity.DepotEntity
import com.adrzdv.mtocp.data.db.entity.DivisionEntity
import com.adrzdv.mtocp.data.db.entity.RevisionTypeEntity
import kotlinx.coroutines.flow.Flow

class DictionaryRepository(
    private val branchDao: BranchDao,
    private val departmentDao: DepartmentDao,
    private val depotDao: DepotDao,
    private val divisionDao: DivisionDao,
    private val revisionTypeDao: RevisionTypeDao
) {
    val branches: Flow<List<BranchEntity>> = branchDao.getAllBranches()

    val departments: Flow<List<DepartmentEntity>> = departmentDao.getAllDepartments()

    val divisions: Flow<List<DivisionEntity>> = divisionDao.getAllDivisions()

    val revisionTypes: Flow<List<RevisionTypeEntity>> = revisionTypeDao.getAllRevisionTypes()

    suspend fun getBranchById(id: Int) = branchDao.getBranchById(id)

    suspend fun getDepartmentById(id: Int) = departmentDao.getDepartmentById(id)

    suspend fun syncDictionaries(
        newBranches: List<BranchEntity>? = null,
        newDepartments: List<DepartmentEntity>? = null,
        newDepots: List<DepotEntity>? = null,
        newDivisions: List<DivisionEntity>? = null,
        newRevisionTypes: List<RevisionTypeEntity>? = null
    ) {
        newBranches?.let {
            branchDao.deactivateAllBranches()
            branchDao.insertBranches(it)
        }
        newDepartments?.let {
            departmentDao.clearAllDepartments()
            departmentDao.insertDepartments(it)
        }
        newDepots?.let {
            depotDao.deactivateAllDepots()
            depotDao.insertDepots(it)
        }
        newDivisions?.let {
            divisionDao.clearAllDivisions()
            divisionDao.insertDivisions(it)
        }
        newRevisionTypes?.let {
            revisionTypeDao.clearAllRevisionTypes()
            revisionTypeDao.insertRevisionTypes(it)
        }
    }

    suspend fun insertBranch(branch: BranchEntity) = branchDao.insertBranch(branch)

    suspend fun updateDepartment(dept: DepartmentEntity) =
        departmentDao.insertDepartments(listOf(dept))
}
