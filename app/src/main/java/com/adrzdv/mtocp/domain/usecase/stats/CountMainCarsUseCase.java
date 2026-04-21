package com.adrzdv.mtocp.domain.usecase.stats;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;

import java.util.Map;
import java.util.UUID;

public class CountMainCarsUseCase {

    public int execute(Map<UUID, RevisionObject> revMap) {
        return (int) revMap.values().stream()
                .filter(obj -> obj instanceof PassengerCar that && !that.getTrailing())
                .count();
    }
}
