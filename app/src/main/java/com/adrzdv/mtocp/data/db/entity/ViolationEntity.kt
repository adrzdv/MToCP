package com.adrzdv.mtocp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "violations",
    indices = [Index(value = ["code"], name = "idx_violation_code")]
)
data class ViolationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "code")
    val code: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "criteria")
    val criteria: String,

    @ColumnInfo(name = "measure")
    val measure: String,

    @ColumnInfo(name = "is_active", defaultValue = "1")
    val isActive: Boolean = true
)

