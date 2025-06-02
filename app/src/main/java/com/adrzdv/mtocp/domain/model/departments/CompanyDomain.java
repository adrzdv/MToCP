package com.adrzdv.mtocp.domain.model.departments;

import java.time.LocalDate;

public class CompanyDomain {
    private Long els;
    private String name;
    private Boolean isActive;
    private String contractNumber;
    private LocalDate expirationDate;
    private BranchDomain branchDomain;

    public CompanyDomain() {

    }

    public CompanyDomain(Long els,
                         String name,
                         Boolean isActive,
                         String contractNumber,
                         LocalDate expirationDate,
                         BranchDomain branchDomain) {
        this.els = els;
        this.name = name;
        this.isActive = isActive;
        this.contractNumber = contractNumber;
        this.expirationDate = expirationDate;
        this.branchDomain = branchDomain;
    }


    public Long getEls() {
        return els;
    }

    public void setEls(Long els) {
        this.els = els;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BranchDomain getBranchDomain() {
        return branchDomain;
    }

    public void setBranchDomain(BranchDomain branchDomain) {
        this.branchDomain = branchDomain;
    }
}
