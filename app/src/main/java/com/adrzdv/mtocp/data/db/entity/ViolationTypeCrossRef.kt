package com.adrzdv.mtocp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "violations_types",
    primaryKeys = ["violation_id", "type_id"],
    foreignKeys = [
        ForeignKey(
            entity = ViolationEntity::class,
            parentColumns = ["id"],
            childColumns = ["violation_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = RevisionTypeEntity::class, parentColumns = ["id"], childColumns = ["type_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("violation_id"), Index("type_id")]
)
data class ViolationTypeCrossRef(
    @ColumnInfo(name = "violation_id") val violationId: Int,
    @ColumnInfo(name = "type_id") val typeId: Int
)
