package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.Coach;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class TrainOrder extends Order implements CollectableOrder {
    private String route;
    private TrainDomain train;

    public TrainOrder(String numberOrder,
                      LocalDateTime dateStart,
                      LocalDateTime dateEnd,
                      String route) {
        super(numberOrder, dateStart, dateEnd, route);
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public void updateRevisionObject(RevisionObject o) {
        if (o instanceof Coach that) {
            Map<String, RevisionObject> currMap = train.getObjectsMap();
            currMap.put(o.getNumber(), o);
            train.setObjectsMap(currMap);
        }
    }

    @Override
    public void clearCrewWorkers() {
        train.clearCrewMap();
    }

    @Override
    public void clearRevisionObjects() {
        train.clearObjects();
    }

    @Override
    public boolean checkCrew() {
        return train.checkAllCrewIsAdded();
    }

    @Override
    public void deleteCrewWorker(WorkerDomain worker) {
        train.deleteCreWorker(worker);
    }

    @Override
    public void deleteRevisionObject(RevisionObject o) {
        train.deleteRevisionObject(o);
    }

    @Override
    public int countViolations() {
        return train.countViolation();
    }

    @Override
    public void setCollector(ObjectCollector collector) {
        if (collector instanceof TrainDomain that) {
            this.train = that;
        } else {
            throw new IllegalArgumentException("Expected TrainDomain.class; Got: " +
                    collector.getClass().getSimpleName());
        }
    }

    @Override
    public ObjectCollector getCollector() {
        return train;
    }

    @Override
    public void setIsQualityPassport(Boolean isQualityPassport) {
        train.setQualityPassport(isQualityPassport);
    }

    @Override
    public void addViolationInCollector(String objNumber, ViolationDomain violation) {
        train.addViolationToObject(objNumber, violation);
    }

    @Override
    public void deleteViolationInCollector(String objNumber, int code) {
        train.deleteViolationInObject(objNumber, code);
    }

    @Override
    public Map<String, WorkerDomain> getCrewMap() {
        return train.getWorkerMap();
    }

    @Override
    protected void doAddWorker(WorkerDomain worker) {
        if (worker instanceof InnerWorkerDomain innerWorkerDomain) {
            train.addWorker(innerWorkerDomain);
        } else {
            throw new IllegalArgumentException("Expected InnerWorkerDomain.class; Got: " +
                    worker.getClass().getSimpleName());
        }
    }

    @Override
    protected void doAddRevisionObject(RevisionObject o) {
        if (o instanceof PassengerCar passengerCar) {
            if (train.getObjectsMap().containsKey(o.getNumber())) {
                throw new IllegalStateException("Object already exists; Object: " + o.getNumber());
            }
            train.addRevisionObject(passengerCar);
        } else if (o instanceof DinnerCar dinnerCar) {
            if (train.getObjectsMap().containsKey(o.getNumber())) {
                throw new IllegalStateException("Object already exists; Object: " + o.getNumber());
            }
            if (train.getIsDinnerCar()) {
                train.removeDinnerCar();
                train.addRevisionObject(dinnerCar);
            } else {
                train.addRevisionObject(dinnerCar);
            }
        } else {
            throw new IllegalArgumentException("Expected Coach.class; Got: " +
                    o.getClass().getSimpleName());
        }
    }

    public int countViolationsMainCar() {
        return train.getObjectsMap().values().stream()
                .filter(Objects::nonNull)
                .filter(coach -> coach instanceof PassengerCar car && !car.getTrailing())
                .mapToInt(RevisionObject::countViolation)
                .sum();
    }

    public int countViolationTrailingCar() {
        return train.getObjectsMap().values().stream()
                .filter(Objects::nonNull)
                .filter(coach -> coach instanceof PassengerCar car && car.getTrailing())
                .mapToInt(RevisionObject::countViolation)
                .sum();
    }

    public void removeDinnerCoach() {
        train.removeDinnerCar();
    }

}
