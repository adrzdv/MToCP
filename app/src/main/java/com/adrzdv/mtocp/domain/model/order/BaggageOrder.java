package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.BaggageCar;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BaggageOrder extends Order {

    private Map<String, BaggageCar> coachMap;

    public BaggageOrder(String numberOrder,
                        LocalDateTime revisionDateStart,
                        LocalDateTime getRevisionDateEnd,
                        String route) {
        super(numberOrder, revisionDateStart, getRevisionDateEnd, route);
        coachMap = new HashMap<>();
    }
}
