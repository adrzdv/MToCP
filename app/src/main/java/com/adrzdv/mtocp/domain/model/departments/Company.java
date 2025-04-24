package com.adrzdv.mtocp.domain.model.departments;

import java.time.LocalDate;

public class Company {
    private long els;
    private String name;
    private int active;
    private String contractNumber;
    private LocalDate expirationDate;

    public Company() {

    }

    public long getEls() {
        return els;
    }

    public void setEls(long els) {
        this.els = els;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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
}
