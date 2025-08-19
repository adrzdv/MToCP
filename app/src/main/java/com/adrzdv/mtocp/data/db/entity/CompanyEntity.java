package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "companies",
        foreignKeys = @ForeignKey(
                entity = BranchEntity.class,
                parentColumns = "id",
                childColumns = "id_branch"
        ))
public class CompanyEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "els")
    private Long els;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "is_active")
    private Boolean isActive;
    @NonNull
    @ColumnInfo(name = "contract_number")
    private String contractNumber;
    @NonNull
    @ColumnInfo(name = "expiration_date")
    private LocalDate expirationDate;
    @NonNull
    @ColumnInfo(name = "id_branch")
    private Integer branchId;
    @Nullable
    @ColumnInfo(name = "is_dinner_department")
    private Boolean isDinnerDepartment;

    public CompanyEntity(@NonNull Long els,
                         @NonNull String name,
                         @NonNull Boolean isActive,
                         @NonNull String contractNumber,
                         @NonNull LocalDate expirationDate,
                         @NonNull Integer branchId,
                         @Nullable Boolean isDinnerDepartment) {
        this.els = els;
        this.name = name;
        this.isActive = isActive;
        this.contractNumber = contractNumber;
        this.expirationDate = expirationDate;
        this.branchId = branchId;
        this.isDinnerDepartment = isDinnerDepartment;
    }

    public CompanyEntity() {
        this.els = 0L;
        this.name = "";
        this.contractNumber = "";
        this.isActive = true;
        this.expirationDate = LocalDate.of(1900, 1, 1);
        this.branchId = 0;
        this.isDinnerDepartment = false;
    }

    @NonNull
    public Long getEls() {
        return els;
    }

    public void setEls(@NonNull Long els) {
        this.els = els;
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
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(@NonNull String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @NonNull
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(@NonNull LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @NonNull
    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(@NonNull Integer branchId) {
        this.branchId = branchId;
    }

    @Nullable
    public Boolean getDinnerDepartment() {
        return isDinnerDepartment;
    }

    public void setDinnerDepartment(@Nullable Boolean dinnerDepartment) {
        isDinnerDepartment = dinnerDepartment;
    }
}
