package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.departments.DepotDomain;

public class InnerWorkerDomain extends WorkerDomain {
    private DepotDomain depotDomain;

    public InnerWorkerDomain(int id, String name, DepotDomain depotDomain) {
        super(id, name);
        this.depotDomain = depotDomain;
    }

    public DepotDomain getDepot() {
        return depotDomain;
    }

    public void setDepot(DepotDomain depotDomain) {
        this.depotDomain = depotDomain;
    }
}
