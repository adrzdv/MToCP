package com.adrzdv.mtocp.domain.model.revobjectcollectors;

import com.adrzdv.mtocp.domain.model.revisionobject.RevisionObject;

import java.util.HashMap;
import java.util.Map;

public abstract class ObjectCollector {
    private String number;
    private Map<String, RevisionObject> objectsMap;

    public ObjectCollector(String number) {
        this.number = number;
        objectsMap = new HashMap<>();
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

    public int countViolation() {

        return objectsMap.values().stream()
                .mapToInt(RevisionObject::countViolation)
                .sum();
    }
}
