package com.adrzdv.mtocp.domain.model.departments;

import java.util.Objects;

public class BranchDomain {
    private int id;
    private String name;
    private String shortName;

    public BranchDomain(int id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BranchDomain that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
