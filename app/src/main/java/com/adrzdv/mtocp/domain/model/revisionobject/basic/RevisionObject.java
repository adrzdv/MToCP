package com.adrzdv.mtocp.domain.model.revisionobject.basic;

import com.adrzdv.mtocp.domain.model.violation.StaticsParam;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain class model for abstract revision object. Stores data about number of revision object,
 * start and date of revision, indicator of the presence of "Quality Passport", maps of violations
 * and additional statistics parameters
 */
public abstract class RevisionObject {
    private String number;
    private WorkerDomain workerDomain;
    private LocalDateTime revisionDateStart;
    private LocalDateTime revisionDateEnd;
    private Boolean isQualityPassport;
    private Map<String, ViolationDomain> violationMap;
    private Map<String, StaticsParam> additionalParams;

    public RevisionObject(String number) {
        this.number = number;
        this.revisionDateStart = LocalDateTime.now();
        this.isQualityPassport = false;
        this.violationMap = new HashMap<>();
        this.additionalParams = new HashMap<>();
    }

    public int countViolation() {
        return violationMap.values().stream()
                .mapToInt(ViolationDomain::getAmount)
                .sum();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getRevisionDateStart() {
        return revisionDateStart;
    }

    public void setRevisionDateStart(LocalDateTime revisionDateStart) {
        this.revisionDateStart = revisionDateStart;
    }

    public LocalDateTime getRevisionDateEnd() {
        return revisionDateEnd;
    }

    public void setRevisionDateEnd(LocalDateTime revisionDateEnd) {
        this.revisionDateEnd = revisionDateEnd;
    }

    public Boolean getQualityPassport() {
        return isQualityPassport;
    }

    public void setQualityPassport(Boolean qualityPassport) {
        isQualityPassport = qualityPassport;
    }

    public WorkerDomain getWorker() {
        return workerDomain;
    }

    public void setWorker(WorkerDomain workerDomain) {
        this.workerDomain = workerDomain;
    }

    public Map<String, ViolationDomain> getViolationMap() {
        return violationMap;
    }

    public void setViolationMap(Map<String, ViolationDomain> violationMap) {
        this.violationMap = violationMap;
    }

    public Map<String, StaticsParam> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, StaticsParam> additionalParams) {
        this.additionalParams = additionalParams;
    }
}
