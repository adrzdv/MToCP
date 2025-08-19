package com.adrzdv.mtocp.domain.model.revisionobject.collectors;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.util.Collections;
import java.util.Map;

/**
 * Domain class model for ticket office - ticket terminals collector
 */
public class TicketOfficeDomain extends ObjectCollector {

    private String stationName;
    private WorkerDomain headOfOffice;

    public TicketOfficeDomain(String name) {
        super(name);
    }

    @Override
    public Map<String, RevisionObject> getCheckedObjects() {
        return null;
    }


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public WorkerDomain getHeadOfOffice() {
        return headOfOffice;
    }

    public void setHeadOfOffice(WorkerDomain headOfOffice) {
        this.headOfOffice = headOfOffice;
    }

    public void removeOfficeHeader() {
        headOfOffice = null;
    }
}
