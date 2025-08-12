package com.adrzdv.mtocp.domain.usecase;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GetObjectMapSortedWithViolationsUseCase {

    public Set<RevisionObject> execute(Map<String, RevisionObject> revMap) {
        Set<RevisionObject> result = new LinkedHashSet<>();

        for (RevisionObject obj : revMap.values()) {
            if (!obj.getViolationMap().isEmpty()) {
                result.add(obj);
            }
        }
        return result;
    }
}
