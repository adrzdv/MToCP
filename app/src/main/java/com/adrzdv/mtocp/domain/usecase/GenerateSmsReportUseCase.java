package com.adrzdv.mtocp.domain.usecase;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class GenerateSmsReportUseCase {

    public String execute(Map<String, RevisionObject> revMap) throws ExecutionException, InterruptedException {
        StringBuilder result = new StringBuilder();
        StringBuilder dinnerRes = new StringBuilder();
        Map<Integer, ViolationDomain> violationMap = new TreeMap<>();
        Map<Integer, ViolationDomain> violationDinnerMap = new TreeMap<>();

        for (RevisionObject obj : revMap.values()) {
            for (ViolationDomain violation : obj.getViolationMap().values()) {
                ViolationDomain vl = new ViolationDomain(violation.getCode(),
                        violation.getName(),
                        violation.getShortName());
                vl.setAmount(violation.getAmount());
                if (obj instanceof DinnerCar) {
                    violationDinnerMap.merge(violation.getCode(),
                            vl,
                            (oldViolation, newViolation) -> {
                                oldViolation.setAmount(oldViolation.getAmount() + newViolation.getAmount());
                                return oldViolation;
                            });
                } else {
                    violationMap.merge(violation.getCode(),
                            vl,
                            (oldViolation, newViolation) -> {
                                oldViolation.setAmount(oldViolation.getAmount() + newViolation.getAmount());
                                return oldViolation;
                            });
                }
            }
        }

        result.append(appendViolations(violationMap));

        if (revMap.values().stream().anyMatch(dc -> dc instanceof DinnerCar)) {
            dinnerRes.append("__________________\n").append("Вагон-ресторан\n")
                    .append(appendViolations(violationDinnerMap));
            result.append("\n").append(dinnerRes);
        }

        return result.toString();
    }

    private String appendViolations(Map<Integer, ViolationDomain> violationMap) {
        StringBuilder result = new StringBuilder();
        for (ViolationDomain violation : violationMap.values()) {
            result.append(violation.getCode())
                    .append(". ")
                    .append(violation.getShortName())
                    .append("-")
                    .append(violation.getAmount())
                    .append("\n");
        }
        return result.toString();
    }
}
