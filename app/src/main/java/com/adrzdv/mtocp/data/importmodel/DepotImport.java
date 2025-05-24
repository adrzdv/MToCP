package com.adrzdv.mtocp.data.importmodel;

import androidx.annotation.NonNull;

import java.util.Objects;

public class DepotImport {
    private Integer id;
    private String name;
    private String shortName;
    private BranchImport branch;
    private String phoneNumber;

    public DepotImport(@NonNull Integer id,
                       @NonNull String name,
                       @NonNull String shortName,
                       @NonNull BranchImport branch,
                       @NonNull String phoneNumber) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.branch = branch;
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @NonNull
    public BranchImport getBranch() {
        return branch;
    }

    public void setBranch(BranchImport branch) {
        this.branch = branch;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DepotImport that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
