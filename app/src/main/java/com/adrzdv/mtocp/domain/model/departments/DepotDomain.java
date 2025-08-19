package com.adrzdv.mtocp.domain.model.departments;

import java.util.Objects;

public class DepotDomain {
    private int id;
    private String name;
    private String shortName;
    private String phoneNumber;
    private BranchDomain branchDomain;
    private Boolean isActive;
    private Boolean isDinnerDepot;

    public DepotDomain(int id,
                       String name,
                       String shortName,
                       String phoneNumber,
                       BranchDomain branchDomain,
                       Boolean isActive,
                       Boolean isDinnerDepot) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.phoneNumber = phoneNumber;
        this.branchDomain = branchDomain;
        this.isActive = isActive;
        this.isDinnerDepot = isDinnerDepot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String sName) {
        this.shortName = sName;
    }

    public BranchDomain getBranchDomain() {
        return branchDomain;
    }

    public void setBranchDomain(BranchDomain branchDomain) {
        this.branchDomain = branchDomain;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDinnerDepot() {
        return isDinnerDepot;
    }

    public void setDinnerDepot(Boolean dinnerDepot) {
        isDinnerDepot = dinnerDepot;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DepotDomain that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
