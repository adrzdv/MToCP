package com.adrzdv.mtocp.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ViolationWithDepartments(

    @Embedded
    val violation: ViolationEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ViolationDepartmentCrossRef::class,
            parentColumn = "violation_id",
            entityColumn = "department_id"
        )
    )
    val departments: List<DepartmentEntity>
)
