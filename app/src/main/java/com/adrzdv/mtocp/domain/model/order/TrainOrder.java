package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.time.LocalDateTime;

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
            train.getObjectsMap().put(passengerCar.getNumber(), passengerCar);
        } else {
            throw new IllegalArgumentException("Expected PassengerCar.class; Got: " +
                    o.getClass().getSimpleName());
        }
    }
}
