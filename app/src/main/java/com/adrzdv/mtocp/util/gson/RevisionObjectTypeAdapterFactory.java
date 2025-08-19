package com.adrzdv.mtocp.util.gson;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.TicketTerminal;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.BaggageCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;

public class RevisionObjectTypeAdapterFactory {

    public static RuntimeTypeAdapterFactory<RevisionObject> create() {
        return RuntimeTypeAdapterFactory
                .of(RevisionObject.class, "objClass")
                .registerSubtype(PassengerCar.class, PassengerCar.class.getSimpleName())
                .registerSubtype(DinnerCar.class, DinnerCar.class.getSimpleName())
                .registerSubtype(BaggageCar.class, BaggageCar.class.getSimpleName())
                .registerSubtype(TicketTerminal.class, TicketTerminal.class.getSimpleName());
    }
}
