package com.adrzdv.mtocp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "violations_departments",
    primaryKeys = ["violation_id", "department_id"],
    foreignKeys = [
        ForeignKey(
            entity = ViolationEntity::class,
            parentColumns = ["id"],
            childColumns = ["violation_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DepartmentEntity::class,
            parentColumns = ["id"],
            childColumns = ["department_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("violation_id"), Index("department_id")]
)
data class ViolationDepartmentCrossRef(

    @ColumnInfo(name = "violation_id")
    val violationId: Int,

    @ColumnInfo(name = "department_id")
    val departmentId: Int
)
