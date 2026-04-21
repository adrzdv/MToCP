package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.enums.CoachTypes;
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType;

/**
 * Domain class model for dinner coach
 */
public class DinnerCar extends Coach {
    private CompanyDomain companyDomain;
    private DinnerCarsType dinnerType;
    private DepotDomain depot;

    public DinnerCar(String number, CoachTypes type) {
        super(number, type);
    }

    public CompanyDomain getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(CompanyDomain companyDomain) {
        this.companyDomain = companyDomain;
    }

    public DinnerCarsType getDinnerType() {
        return dinnerType;
    }

    public void setType(DinnerCarsType type) {
        this.dinnerType = type;
    }

    public DepotDomain getDepot() {
        return depot;
    }

    public void setDepot(DepotDomain depot) {
        this.depot = depot;
    }

    public void setDinnerType(DinnerCarsType dinnerType) {
        this.dinnerType = dinnerType;
    }
}
