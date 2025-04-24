package com.adrzdv.mtocp.domain.model.revisionobject.coach;

import com.adrzdv.mtocp.domain.model.departments.Depot;
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType;

public class PassengerCar extends Coach {
    private PassengerCoachType coachType;
    private Depot depot;

    public PassengerCar(String number) {
        super(number);
    }

    public PassengerCoachType getCoachType() {
        return coachType;
    }

    public void setCoachType(PassengerCoachType coachType) {
        this.coachType = coachType;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
}
