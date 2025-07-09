package com.adrzdv.mtocp.data.importmodel;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Company import model
 * This model is using for import company data from json
 */
public class CompanyImport {
    private Long els;
    private String name;
    private Boolean isActive;
    private String contract;
    private LocalDate expirationDate;
    private Integer branchId;
    private Boolean isDinnerDepartment;

    public CompanyImport(@NonNull Long els,
                         @NonNull String name,
                         @NonNull Boolean isActive,
                         @NonNull String contract,
                         @NonNull LocalDate expirationDate,
                         @NonNull Integer branchId,
                         @NonNull Boolean isDinnerDepartment) {
        this.els = els;
        this.name = name;
        this.isActive = isActive;
        this.contract = contract;
        this.expirationDate = expirationDate;
        this.branchId = branchId;
        this.isDinnerDepartment = isDinnerDepartment;
    }

    @NonNull
    public Long getEls() {
        return els;
    }

    public void setEls(Long els) {
        this.els = els;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @NonNull
    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    @NonNull
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @NonNull
    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Boolean getDinnerDepartment() {
        return isDinnerDepartment;
    }

    public void setDinnerDepartment(Boolean dinnerDepartment) {
        isDinnerDepartment = dinnerDepartment;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CompanyImport that)) return false;
        return Objects.equals(els, that.els)
                && Objects.equals(branchId, that.branchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(els, branchId);
    }


}
