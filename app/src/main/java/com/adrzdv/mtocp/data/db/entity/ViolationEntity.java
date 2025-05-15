package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "violations")
public class ViolationEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private Integer id;
    @NonNull
    @ColumnInfo(name = "code")
    private Integer code;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "short_name")
    private String shortName;
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
    @NonNull
    @ColumnInfo(name = "at_ticket_office", defaultValue = "0")
    private Boolean atTicketOffice;

    public ViolationEntity() {
        this.id = 0;
        this.code = 0;
        this.name = "";
        this.shortName = "";
        this.isActive = false;
        this.inTransit = false;
        this.atStartPoint = false;
        this.atTurnroundPoint = false;
        this.atTicketOffice = false;

    }

    @Ignore
    public ViolationEntity(@NonNull Integer code,
                           @NonNull String name,
                           @NonNull String shortName,
                           @NonNull Boolean isActive,
                           @NonNull Boolean inTransit,
                           @NonNull Boolean atStartPoint,
                           @NonNull Boolean atTurnroundPoint,
                           @NonNull Boolean atTicketOffice) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.isActive = isActive;
        this.inTransit = inTransit;
        this.atStartPoint = atStartPoint;
        this.atTurnroundPoint = atTurnroundPoint;
        this.atTicketOffice = atTicketOffice;
    }

    @NonNull
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

    @NonNull
    public String getShortName() {
        return shortName;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setShortName(@NonNull String shortName) {
        this.shortName = shortName;
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

    @NonNull
    public Boolean getAtTicketOffice() {
        return atTicketOffice;
    }

    public void setAtTicketOffice(@NonNull Boolean atTicketOffice) {
        this.atTicketOffice = atTicketOffice;
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
