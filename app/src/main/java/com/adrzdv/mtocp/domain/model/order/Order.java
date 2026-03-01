package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;
import com.adrzdv.mtocp.domain.validation.RegularValidator;

import java.time.LocalDateTime;

public abstract class Order extends RegularValidator {
    private String number;
    private LocalDateTime revisionDateStart;
    private LocalDateTime getRevisionDateEnd;
    private String route;
    private RevisionType revisionType;

    public Order(String number,
                 LocalDateTime revisionDateStart,
                 LocalDateTime getRevisionDateEnd,
                 String route) {
        this.number = number;
        this.revisionDateStart = revisionDateStart;
        this.getRevisionDateEnd = getRevisionDateEnd;
        this.route = route;
    }

    public Order() {
        this.number = "";
        this.revisionDateStart = LocalDateTime.now();
        this.getRevisionDateEnd = LocalDateTime.now();
        this.route = "";
    }

    public void setRevisionType(RevisionType revisionType) {
        this.revisionType = revisionType;
    }

    public RevisionType getRevisionType() {
        return this.revisionType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getRevisionDateStart() {
        return revisionDateStart;
    }

    public void setRevisionDateStart(LocalDateTime revisionDateStart) {
        this.revisionDateStart = revisionDateStart;
    }

    public LocalDateTime getGetRevisionDateEnd() {
        return getRevisionDateEnd;
    }

    public void setGetRevisionDateEnd(LocalDateTime getRevisionDateEnd) {
        this.getRevisionDateEnd = getRevisionDateEnd;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public abstract void updateRevisionObject(RevisionObject object);

    public abstract void updateRevisionObjectFromJson(RevisionObject object);

    public abstract void clearCrewWorkers();

    public abstract void clearRevisionObjects();

    public abstract boolean checkCrew();

    public abstract void deleteCrewWorker(WorkerDomain worker);

    public abstract void deleteRevisionObject(RevisionObject o);

    public abstract int countViolations();
}
