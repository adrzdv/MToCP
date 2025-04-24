package com.adrzdv.mtocp.domain.model.revisionobject.coach;

import com.adrzdv.mtocp.domain.model.departments.Company;
import com.adrzdv.mtocp.domain.model.revisionobject.RevisionObject;

public class BaggageCar extends Coach {
    private Company company;

    public BaggageCar(String number) {
        super(number);
    }

}
