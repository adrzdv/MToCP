package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes;

public class OuterWorkerDomain extends WorkerDomain {
    private CompanyDomain companyDomain;

    public OuterWorkerDomain(int id, String name, CompanyDomain companyDomain, WorkerTypes type) {
        super(id, name, type);
        this.companyDomain = companyDomain;
    }

    public CompanyDomain getCompany() {
        return companyDomain;
    }

    public void setCompany(CompanyDomain companyDomain) {
        this.companyDomain = companyDomain;
    }
}
