package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType;

/**
 * Domain class model for dinner coach
 */
public class DinnerCar extends Coach {
    private CompanyDomain companyDomain;
    private DinnerCarsType type;
    private DepotDomain depot;

    public DinnerCar(String number) {
        super(number);
    }

    public CompanyDomain getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(CompanyDomain companyDomain) {
        this.companyDomain = companyDomain;
    }

    public DinnerCarsType getType() {
        return type;
    }

    public void setType(DinnerCarsType type) {
        this.type = type;
    }

    public DepotDomain getDepot() {
        return depot;
    }

    public void setDepot(DepotDomain depot) {
        this.depot = depot;
    }
}
