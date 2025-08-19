package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.BaggageCar;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.domain.model.workers.OuterWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BaggageOrder extends Order {

    private Map<String, BaggageCar> coachMap;
    private Map<Integer, OuterWorkerDomain> workerMap;

    public BaggageOrder(String numberOrder,
                        LocalDateTime revisionDateStart,
                        LocalDateTime getRevisionDateEnd,
                        String route) {
        super(numberOrder, revisionDateStart, getRevisionDateEnd, route);
        coachMap = new HashMap<>();
        workerMap = new HashMap<>();
    }

    @Override
    public void updateRevisionObject(RevisionObject object) {
        if (object instanceof BaggageCar that) {
            coachMap.put(that.getNumber(), that);
        }
    }

    @Override
    public void updateRevisionObjectFromJson(RevisionObject object) {

    }

    @Override
    public void clearCrewWorkers() {
        workerMap.clear();
    }

    @Override
    public void clearRevisionObjects() {
        coachMap.clear();
    }

    @Override
    public boolean checkCrew() {
        return !workerMap.isEmpty();
    }

    @Override
    public void deleteCrewWorker(WorkerDomain worker) {
        workerMap.remove(worker.getId());
    }

    @Override
    public void deleteRevisionObject(RevisionObject o) {
        coachMap.remove(o.getNumber());
    }

    @Override
    public int countViolations() {
        return coachMap.values().stream()
                .mapToInt(RevisionObject::countViolation)
                .sum();
    }

    @Override
    protected void doAddWorker(WorkerDomain worker) {
        if (worker instanceof OuterWorkerDomain that) {
            workerMap.put(that.getId(), that);
        }
    }

    @Override
    protected void doAddRevisionObject(RevisionObject o) {
        if (o instanceof BaggageCar baggageCar) {
            if (coachMap.containsKey(o.getNumber())) {
                throw new IllegalArgumentException(MessageCodes.DUPLICATE_ERROR.getErrorTitle());
            }
            coachMap.put(baggageCar.getNumber(), baggageCar);
        } else {
            throw new IllegalArgumentException("Expected BaggageCar.class; Got: "
                    + o.getClass().getSimpleName());
        }
    }

    public void addViolation(String objNumber, ViolationDomain violation) {
        if (!coachMap.containsKey(objNumber) && coachMap.get(objNumber) == null) {
            throw new IllegalArgumentException(MessageCodes.NOT_FOUND_ERROR.getErrorTitle());
        }
        coachMap.get(objNumber).addViolation(violation);
    }

    public void deleteViolation(String objNumber, int code) {
        if (!coachMap.containsKey(objNumber) && coachMap.get(objNumber) == null) {
            throw new IllegalArgumentException(MessageCodes.NOT_FOUND_ERROR.getErrorTitle());
        }
        coachMap.get(objNumber).deleteViolation(code);
    }

    public Map<String, BaggageCar> getCoachMap() {
        return coachMap;
    }
}
