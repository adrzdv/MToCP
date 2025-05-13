package com.adrzdv.mtocp.domain.model.revisionobject;

import com.adrzdv.mtocp.domain.model.enums.RevisionObjectType;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.TicketTermial;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.BaggageCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;

public class RevisionObjectFactory {

    public static RevisionObject createRevisionObject(RevisionObjectType objectType, String number) {
        return switch (objectType) {
            case PASSENGER -> new PassengerCar(number);
            case DINNER -> new DinnerCar(number);
            case BAGGAGE -> new BaggageCar(number);
            case TICKET_OFFICE -> new TicketTermial(number);
        };
    }
}
