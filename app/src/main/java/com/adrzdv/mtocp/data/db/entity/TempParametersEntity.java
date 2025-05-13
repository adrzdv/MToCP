package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

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
    @ColumnInfo(name = "date_start", defaultValue = "1970-01-01")
    private LocalDate dateStart;
    @NonNull
    @ColumnInfo(name = "date_end", defaultValue = "1970-01-01")
    private LocalDate dateEnd;
    @NonNull
    @ColumnInfo(name = "is_train", defaultValue = "0")
    private Boolean isTrain;

    public TempParametersEntity() {
        this.name = "";
        this.isActive = false;
        this.dateStart = LocalDate.of(1970, 1, 1);
        this.dateEnd = LocalDate.of(1970, 1, 1);
        this.isTrain = false;
    }

    public TempParametersEntity(@NonNull String name,
                                @NonNull Boolean isActive,
                                @NonNull LocalDate dateStart,
                                @NonNull LocalDate dateEnd,
                                @NonNull Boolean isTrain) {
        this.name = name;
        this.isActive = isActive;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isTrain = isTrain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(@NonNull Boolean active) {
        isActive = active;
    }

    @NonNull
    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(@NonNull LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    @NonNull
    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(@NonNull LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @NonNull
    public Boolean getTrain() {
        return isTrain;
    }

    public void setTrain(@NonNull Boolean train) {
        isTrain = train;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TempParametersEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
