package com.adrzdv.mtocp.domain.model.revisionobject.collectors;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.violation.StaticsParam;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Domain class model for abstract collectors of revision objects.
 * Stores data of its number, Maps of revision objects, additional static parameters
 * Also stores data about revision order
 */
public abstract class ObjectCollector {
    private String number;
    private Boolean isQualityPassport;
    private Map<String, RevisionObject> objectsMap;
    private Map<String, StaticsParam> additionalParams;

    public ObjectCollector(String number) {
        this.number = number;
        this.isQualityPassport = false;
        objectsMap = new LinkedHashMap<>();
        additionalParams = new HashMap<>();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Map<String, RevisionObject> getObjectsMap() {
        return objectsMap;
    }

    public void setObjectsMap(Map<String, RevisionObject> objectsMap) {
        this.objectsMap = objectsMap;
    }

    public Map<String, StaticsParam> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, StaticsParam> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public Boolean getQualityPassport() {
        return isQualityPassport;
    }

    public void setQualityPassport(Boolean qualityPassport) {
        isQualityPassport = qualityPassport;
    }

    public int countViolation() {

        return objectsMap.values().stream()
                .mapToInt(RevisionObject::countViolation)
                .sum();
    }

    public int countObjects() {

        return objectsMap.size();
    }
}
