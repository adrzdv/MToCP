package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;
import com.adrzdv.mtocp.domain.model.enums.CoachTypes;

/**
 * Domain class model for baggage car
 */
public class BaggageCar extends Coach {
    private CompanyDomain companyDomain;
    private String transportationDocument;

    public BaggageCar(String number, CoachTypes types) {
        super(number, types);
        transportationDocument = "";
    }

    public CompanyDomain getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(CompanyDomain companyDomain) {
        this.companyDomain = companyDomain;
    }

    public String getTransportationDocument() {
        return transportationDocument;
    }

    public void setTransportationDocument(String transportationDocument) {
        this.transportationDocument = transportationDocument;
    }
}
