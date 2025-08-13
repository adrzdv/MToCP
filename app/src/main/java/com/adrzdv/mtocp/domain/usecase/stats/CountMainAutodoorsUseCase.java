package com.adrzdv.mtocp.domain.usecase.stats;


import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;

import java.util.Map;

public class CountMainAutodoorsUseCase {

    public int execute(Map<String, RevisionObject> revMap) {
        return (int) revMap.values().stream()
                .filter(object ->
                        object instanceof PassengerCar that
                                && !that.getTrailing()
                                && that.getAdditionalParams()
                                .get("Автоматические двери")
                                .getCompleted()
                )
                .count();
    }
}
