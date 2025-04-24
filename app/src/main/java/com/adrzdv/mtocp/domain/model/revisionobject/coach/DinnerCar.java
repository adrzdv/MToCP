package com.adrzdv.mtocp.domain.model.revisionobject.coach;

import com.adrzdv.mtocp.domain.model.departments.Company;
import com.adrzdv.mtocp.domain.model.revisionobject.RevisionObject;

public class DinnerCar extends Coach {
    private Company company;

    public DinnerCar(String number) {
        super(number);
    }

}
