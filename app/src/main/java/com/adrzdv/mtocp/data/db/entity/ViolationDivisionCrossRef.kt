package com.adrzdv.mtocp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "violations_divisions",
    primaryKeys = ["division_id", "violation_id"],
    foreignKeys = [
        ForeignKey(entity = DivisionEntity::class, parentColumns = ["id"], childColumns = ["division_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = ViolationEntity::class, parentColumns = ["id"], childColumns = ["violation_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("division_id"), Index("violation_id")]
)
data class ViolationDivisionCrossRef(
    @ColumnInfo(name = "division_id") val divisionId: Int,
    @ColumnInfo(name = "violation_id") val violationId: Int
)
