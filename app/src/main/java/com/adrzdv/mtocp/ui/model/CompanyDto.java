package com.adrzdv.mtocp.ui.model;

import java.time.LocalDate;

public class CompanyDto {
    private Long els;
    private String name;
    private Boolean isActive;
    private String contractNumber;
    private LocalDate expirationDate;
    private String branch;

    public CompanyDto(Long els,
                      String name,
                      Boolean isActive,
                      String contractNumber,
                      LocalDate expirationDate,
                      String branch) {
        this.els = els;
        this.name = name;
        this.isActive = isActive;
        this.contractNumber = contractNumber;
        this.expirationDate = expirationDate;
        this.branch = branch;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}


