package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.departments.Depot;

public class InnerWorker extends Worker {
    private Depot depot;

    public InnerWorker(int id, String name, Depot depot) {
        super(id, name);
        this.depot = depot;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
}
