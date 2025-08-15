package com.adrzdv.mtocp.domain.usecase;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GenerateSmsReportUseCase {

    public String execute(Map<String, RevisionObject> revMap) throws ExecutionException, InterruptedException {
        StringBuilder result = new StringBuilder();
        StringBuilder dinnnerRes = new StringBuilder();
        Map<Integer, ViolationDomain> violationMap = new TreeMap<>();
        Map<Integer, ViolationDomain> violationDinnerMap = new TreeMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (RevisionObject obj : revMap.values()) {
            for (ViolationDomain violation : obj.getViolationMap().values()) {
                ViolationDomain vl = new ViolationDomain(violation.getCode(),
                        violation.getName(),
                        violation.getShortName());
                vl.setAmount(violation.getAmount());
                if (obj instanceof DinnerCar that) {
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

        Future<?> mainViolationFuture = executor.submit(() -> {
            for (ViolationDomain violation : violationMap.values()) {
                result.append(violation.getCode())
                        .append("-")
                        .append(violation.getShortName())
                        .append("-")
                        .append(violation.getAmount())
                        .append("\n");
            }
        });

        Future<?> dinnerViolationFuture = executor.submit(() -> {
            dinnnerRes.append("__________________\n").append("Вагон-ресторан\n");
            for (ViolationDomain violation : violationDinnerMap.values()) {
                dinnnerRes.append(violation.getCode())
                        .append("-")
                        .append(violation.getShortName())
                        .append("-")
                        .append(violation.getAmount())
                        .append("\n");
            }
        });

        mainViolationFuture.get();
        dinnerViolationFuture.get();

        executor.shutdown();
        result.append("\n").append(dinnnerRes);

        return result.toString();
    }
}
