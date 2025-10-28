package com.adrzdv.mtocp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "coaches"
)
data class KriCoachEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: String = ""
)