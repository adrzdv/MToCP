package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "temp_parameters")
public class TempParametersEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @NonNull
    @ColumnInfo(name = "name", defaultValue = "0")
    private String name;
    @NonNull
    @ColumnInfo(name = "is_active", defaultValue = "0")
    private Boolean isActive;
    @NonNull
    @ColumnInfo(name = "date_start", defaultValue = "0000-00-00")
    private LocalDate dateStart;
    @NonNull
    @ColumnInfo(name = "date_end", defaultValue = "0000-00-00")
    private LocalDate dateEnd;
    @NonNull
    @ColumnInfo(name = "is_train", defaultValue = "0")
    private Boolean isTrain;

}
