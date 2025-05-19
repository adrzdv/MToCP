package com.adrzdv.mtocp.data.importmodel;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ViolationImport {

    private Integer id;
    private Integer code;
    private String name;

    private String shortName;
    private Boolean isActive;

    private Boolean inTransit;
    private Boolean atStartPoint;
    private Boolean atTurnroundPoint;
    private Boolean atTicketOffice;

    public ViolationImport(@NonNull Integer id,
                           @NonNull Integer code,
                           @NonNull String name,
                           @NonNull String shortName,
                           @NonNull Boolean isActive,
                           @NonNull Boolean inTransit,
                           @NonNull Boolean atStartPoint,
                           @NonNull Boolean atTurnroundPoint,
                           @NonNull Boolean atTicketOffice) {
        this.id = id;
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
        if (!(o instanceof ViolationImport that)) return false;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
