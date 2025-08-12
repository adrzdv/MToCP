package com.adrzdv.mtocp.domain.usecase;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetGlobalViolationStringUseCase {

    public List<String> execute(Map<String, RevisionObject> objectMap) {
        List<String> result = new ArrayList<>();
        Map<Integer, ViolationDomain> tempViolationMap = new HashMap<>();

        for (RevisionObject obj : objectMap.values()) {
            for (ViolationDomain violation : obj.getViolationMap().values()) {
                ViolationDomain newVl = new ViolationDomain(violation.getCode(),
                        violation.getName(),
                        violation.getShortName());
                newVl.setAmount(violation.getAmount());
                tempViolationMap.merge(violation.getCode(),
                        newVl,
                        (existing, incoming) -> {
                            existing.setAmount(existing.getAmount() + incoming.getAmount());
                            return existing;
                        });
            }
        }
        for (ViolationDomain violationDomain : tempViolationMap.values()) {
            result.add(violationDomain.getCode() + ". " + violationDomain.getName() +
                    ": " + violationDomain.getAmount());
        }

        return result;
    }
}
