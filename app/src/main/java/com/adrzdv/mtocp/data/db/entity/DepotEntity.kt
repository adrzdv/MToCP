package com.adrzdv.mtocp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "depots",
    foreignKeys = [
        ForeignKey(
            entity = BranchEntity::class,
            parentColumns = ["id"],
            childColumns = ["branch_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("branch_id")]
)
data class DepotEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "short_name")
    val shortName: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean,

    @ColumnInfo(name = "branch_id")
    val branchId: Int,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String
)
