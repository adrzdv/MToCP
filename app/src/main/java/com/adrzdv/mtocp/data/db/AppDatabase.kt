package com.adrzdv.mtocp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrzdv.mtocp.data.db.converter.DataConverter
import com.adrzdv.mtocp.data.db.dao.BranchDao
import com.adrzdv.mtocp.data.db.dao.CompanyDao
import com.adrzdv.mtocp.data.db.dao.DepartmentDao
import com.adrzdv.mtocp.data.db.dao.DepotDao
import com.adrzdv.mtocp.data.db.dao.DivisionDao
import com.adrzdv.mtocp.data.db.dao.KriCoachDao
import com.adrzdv.mtocp.data.db.dao.RevisionTypeDao
import com.adrzdv.mtocp.data.db.dao.TempParamsDao
import com.adrzdv.mtocp.data.db.dao.TrainDao
import com.adrzdv.mtocp.data.db.dao.ViolationDao
import com.adrzdv.mtocp.data.db.entity.BranchEntity
import com.adrzdv.mtocp.data.db.entity.CompanyEntity
import com.adrzdv.mtocp.data.db.entity.DepartmentEntity
import com.adrzdv.mtocp.data.db.entity.DepotEntity
import com.adrzdv.mtocp.data.db.entity.DivisionEntity
import com.adrzdv.mtocp.data.db.entity.KriCoachEntity
import com.adrzdv.mtocp.data.db.entity.ProgressiveDatesEntity
import com.adrzdv.mtocp.data.db.entity.RevisionTypeEntity
import com.adrzdv.mtocp.data.db.entity.TempParametersEntity
import com.adrzdv.mtocp.data.db.entity.TrainEntity
import com.adrzdv.mtocp.data.db.entity.ViolationDepartmentCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationDivisionCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.data.db.entity.ViolationTypeCrossRef

@Database(
    entities = [
        TempParametersEntity::class,
        CompanyEntity::class,
        TrainEntity::class,
        KriCoachEntity::class,
        ProgressiveDatesEntity::class,
        ViolationEntity::class,
        BranchEntity::class,
        DepotEntity::class,
        DepartmentEntity::class,
        DivisionEntity::class,
        RevisionTypeEntity::class,
        ViolationDepartmentCrossRef::class,
        ViolationDivisionCrossRef::class,
        ViolationTypeCrossRef::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun violationDao(): ViolationDao
    abstract fun depotDao(): DepotDao
    abstract fun branchDao(): BranchDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun divisionDao(): DivisionDao
    abstract fun revisionTypeDao(): RevisionTypeDao
    abstract fun companyDao(): CompanyDao
    abstract fun trainDao(): TrainDao
    abstract fun tempParamsDao(): TempParamsDao
    abstract fun kriCoachDao(): KriCoachDao

}
