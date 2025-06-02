package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;

public class OuterWorker extends Worker {
    private CompanyDomain companyDomain;

    public OuterWorker(int id, String name, CompanyDomain companyDomain) {
        super(id, name);
        this.companyDomain = companyDomain;
    }

    public CompanyDomain getCompany() {
        return companyDomain;
    }

    public void setCompany(CompanyDomain companyDomain) {
        this.companyDomain = companyDomain;
    }
}
