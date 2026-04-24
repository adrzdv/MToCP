package com.adrzdv.mtocp.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ViolationFullInfo(
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
    val departments: List<DepartmentEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ViolationDivisionCrossRef::class,
            parentColumn = "violation_id",
            entityColumn = "division_id"
        )
    )
    val divisions: List<DivisionEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ViolationTypeCrossRef::class,
            parentColumn = "violation_id",
            entityColumn = "type_id"
        )
    )
    val revisionTypes: List<RevisionTypeEntity>
)

