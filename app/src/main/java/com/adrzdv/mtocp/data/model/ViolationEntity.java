package com.adrzdv.mtocp.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "violations")
public class ViolationEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @NonNull
    @ColumnInfo(name = "code")
    private Integer code;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "is_active", defaultValue = "0")
    private Boolean isActive;
    @NonNull
    @ColumnInfo(name = "in_transit", defaultValue = "0")
    private Boolean inTransit;
    @NonNull
    @ColumnInfo(name = "at_start_point", defaultValue = "0")
    private Boolean atStartPoint;
    @NonNull
    @ColumnInfo(name = "at_turnround_point", defaultValue = "0")
    private Boolean atTurnroundPoint;

    public ViolationEntity() {
        this.code = 0;
        this.name = "";
        this.isActive = false;
        this.inTransit = false;
        this.atStartPoint = false;
        this.atTurnroundPoint = false;
    }

    public ViolationEntity(@NonNull Integer code,
                           @NonNull String name,
                           @NonNull Boolean isActive,
                           @NonNull Boolean inTransit,
                           @NonNull Boolean atStartPoint,
                           @NonNull Boolean atTurnroundPoint) {
        this.code = code;
        this.name = name;
        this.isActive = isActive;
        this.inTransit = inTransit;
        this.atStartPoint = atStartPoint;
        this.atTurnroundPoint = atTurnroundPoint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public Integer getCode() {
        return code;
    }

    public void setCode(@NonNull Integer code) {
        this.code = code;
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
    public Boolean getInTransit() {
        return inTransit;
    }

    public void setInTransit(@NonNull Boolean inTransit) {
        this.inTransit = inTransit;
    }

    @NonNull
    public Boolean getAtStartPoint() {
        return atStartPoint;
    }

    public void setAtStartPoint(@NonNull Boolean atStartPoint) {
        this.atStartPoint = atStartPoint;
    }

    @NonNull
    public Boolean getAtTurnroundPoint() {
        return atTurnroundPoint;
    }

    public void setAtTurnroundPoint(@NonNull Boolean atTurnroundPoint) {
        this.atTurnroundPoint = atTurnroundPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ViolationEntity that)) return false;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
