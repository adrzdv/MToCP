package com.adrzdv.mtocp.domain.model.violation;

import java.util.Map;

public class ViolationObject {
    private String name;
    private int amount;
    private Map<String, String> attributeMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
