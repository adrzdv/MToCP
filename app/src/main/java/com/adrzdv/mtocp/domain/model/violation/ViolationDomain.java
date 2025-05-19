package com.adrzdv.mtocp.domain.model.violation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViolationDomain {
    private Integer code;
    private String name;
    private String shortName;
    private int amount;
    private Map<String, String> attributeMap;

    public ViolationDomain(Integer code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.amount = 1;
        this.attributeMap = new HashMap<>();
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

    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
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


}
