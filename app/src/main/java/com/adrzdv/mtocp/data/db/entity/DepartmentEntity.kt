package com.adrzdv.mtocp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "departments")
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "short_name")
    val shortName: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean
)
