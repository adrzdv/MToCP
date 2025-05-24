package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.departments.DepotDomain;

public class InnerWorker extends Worker {
    private DepotDomain depotDomain;

    public InnerWorker(int id, String name, DepotDomain depotDomain) {
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
