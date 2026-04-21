package com.adrzdv.mtocp.domain.model.revisionobject;

import com.adrzdv.mtocp.domain.model.enums.CoachTypes;
import com.adrzdv.mtocp.domain.model.enums.RevisionObjectType;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.TicketTerminal;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.BaggageCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;

public class RevisionObjectFactory {

    public static RevisionObject createRevisionObject(RevisionObjectType objectType, String number, CoachTypes type) {
        return switch (objectType) {
            case PASSENGER -> new PassengerCar(number, type);
            case DINNER -> new DinnerCar(number, type);
            case BAGGAGE -> new BaggageCar(number, type);
            case TICKET_OFFICE -> new TicketTerminal(number);
        };
    }
}
