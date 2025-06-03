package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;

/**
 * Domain class model for baggage car
 */
public class BaggageCar extends Coach {
    private CompanyDomain companyDomain;

    public BaggageCar(String number) {
        super(number);
    }

}
