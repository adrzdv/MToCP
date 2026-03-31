package com.adrzdv.mtocp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "progress_period",
    foreignKeys = [
        ForeignKey(
            entity = TrainEntity::class,
            parentColumns = ["id"],
            childColumns = ["train_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProgressiveDatesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "train_id")
    val trainId: Int,
    @ColumnInfo(name = "date_start")
    val dateStart: LocalDate?,
    @ColumnInfo("date_end")
    val dateEnd: LocalDate?
)