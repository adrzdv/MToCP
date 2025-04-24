package com.adrzdv.mtocp.domain.model.workers;

import com.adrzdv.mtocp.domain.model.departments.Company;

public class OuterWorker extends Worker {
    private Company company;

    public OuterWorker(int id, String name, Company company) {
        super(id, name);
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
