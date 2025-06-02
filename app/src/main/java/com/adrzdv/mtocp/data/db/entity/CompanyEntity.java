package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "companies",
        foreignKeys = @ForeignKey(
                entity = BranchEntity.class,
                parentColumns = "id",
                childColumns = "id_branch",
                onDelete = ForeignKey.CASCADE
        ))
public class CompanyEntity {
    @NonNull
    @PrimaryKey(autoGenerate = false)
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

    public CompanyEntity(@NonNull Long els,
                         @NonNull String name,
                         @NonNull Boolean isActive,
                         @NonNull String contractNumber,
                         @NonNull LocalDate expirationDate,
                         @NonNull Integer branchId) {
        this.els = els;
        this.name = name;
        this.isActive = isActive;
        this.contractNumber = contractNumber;
        this.expirationDate = expirationDate;
        this.branchId = branchId;
    }

    public CompanyEntity() {
        this.els = 0L;
        this.name = "";
        this.contractNumber = "";
        this.isActive = true;
        this.expirationDate = LocalDate.of(1900, 1, 1);
        this.branchId = 0;
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
}
