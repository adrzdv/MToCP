package com.adrzdv.mtocp.domain.model.violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViolationDomain {
    private Integer code;
    private String name;
    private String shortName;
    private int amount;
    private List<String> attributeList;
    private boolean isResolved;
    private List<String> mediaPaths;

    public ViolationDomain(Integer code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.amount = 1;
        this.attributeList = new ArrayList<>();
        this.isResolved = false;
        this.mediaPaths = new ArrayList<>();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<String> getAttributeMap() {
        return attributeList;
    }

    public void setAttributeMap(List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ViolationDomain that)) return false;
        return amount == that.amount && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }


    public List<String> getMediaPaths() {
        return mediaPaths;
    }

    public void setMediaPaths(List<String> mediaPaths) {
        this.mediaPaths = mediaPaths;
    }
}
