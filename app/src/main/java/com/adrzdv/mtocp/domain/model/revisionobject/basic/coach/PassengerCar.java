package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType;

public class PassengerCar extends Coach {
    private PassengerCoachType coachType;
    private DepotDomain depotDomain;

    public PassengerCar(String number) {
        super(number);
    }

    public PassengerCoachType getCoachType() {
        return coachType;
    }

    public void setCoachType(PassengerCoachType coachType) {
        this.coachType = coachType;
    }

    public DepotDomain getDepot() {
        return depotDomain;
    }

    public void setDepot(DepotDomain depotDomain) {
        this.depotDomain = depotDomain;
    }
}
