package com.adrzdv.mtocp.ui.model;

import java.util.Objects;

public class DepotDto {
    private String name;
    private String shortName;
    private String phoneNumber;
    private String branchName;
    private String branchShortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchShortName() {
        return branchShortName;
    }

    public void setBranchShortName(String branchShortName) {
        this.branchShortName = branchShortName;
    }

    public DepotDto(String name,
                    String shortName,
                    String phoneNumber,
                    String branchName,
                    String branchShortName) {
        this.name = name;
        this.shortName = shortName;
        this.phoneNumber = phoneNumber;
        this.branchName = branchName;
        this.branchShortName = branchShortName;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DepotDto depotDto)) return false;
        return Objects.equals(name, depotDto.name)
                && Objects.equals(shortName, depotDto.shortName)
                && Objects.equals(phoneNumber, depotDto.phoneNumber)
                && Objects.equals(branchName, depotDto.branchName)
                && Objects.equals(branchShortName, depotDto.branchShortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shortName, phoneNumber, branchName, branchShortName);
    }
}
