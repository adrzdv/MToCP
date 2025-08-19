package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes;

public class InnerWorkerDomain extends WorkerDomain {
    private DepotDomain depotDomain;

    public InnerWorkerDomain(int id, String name, DepotDomain depotDomain, WorkerTypes type) {
        super(id, name, type);
        this.depotDomain = depotDomain;
    }

    public DepotDomain getDepotDomain() {
        return depotDomain;
    }

    public void setDepotDomain(DepotDomain depotDomain) {
        this.depotDomain = depotDomain;
    }
}
