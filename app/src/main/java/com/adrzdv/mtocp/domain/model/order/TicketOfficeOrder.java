package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.TicketTerminal;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TicketOfficeDomain;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TicketOfficeOrder extends Order implements CollectableOrder {

    private TicketOfficeDomain ticketOffice;

    public TicketOfficeOrder(String numberOrder,
                             LocalDateTime revisionDateStart,
                             LocalDateTime getRevisionDateEnd,
                             String route) {
        super(numberOrder, revisionDateStart, getRevisionDateEnd, route);
    }

    @Override
    public void updateRevisionObject(RevisionObject o) {
        if (o instanceof TicketTerminal that) {
            ticketOffice.getObjectsMap().put(that.getNumber(), that);
        }
    }

    @Override
    public void updateRevisionObjectFromJson(RevisionObject object) {

    }

    @Override
    public void clearCrewWorkers() {
        ticketOffice.removeOfficeHeader();
    }

    @Override
    public void clearRevisionObjects() {
        ticketOffice.clearObjects();
    }

    @Override
    public boolean checkCrew() {
        return ticketOffice.getHeadOfOffice() != null;
    }

    @Override
    public void deleteCrewWorker(WorkerDomain worker) {
        clearCrewWorkers();
    }

    @Override
    public void deleteRevisionObject(RevisionObject o) {
        ticketOffice.getObjectsMap().remove(o.getNumber());
    }

    @Override
    public int countViolations() {
        return ticketOffice.countViolation();
    }

    @Override
    public void setCollector(ObjectCollector collector) {
        if (collector instanceof TicketOfficeDomain that) {
            this.ticketOffice = that;
        } else {
            throw new IllegalArgumentException("Expected TicketOfficeDomain.class; Got: " +
                    collector.getClass().getSimpleName());
        }
    }

    @Override
    public ObjectCollector getCollector() {
        return ticketOffice;
    }

    @Override
    public void setIsQualityPassport(Boolean isQualityPassport) {
        ticketOffice.setQualityPassport(isQualityPassport);
    }

    @Override
    public Map<String, WorkerDomain> getCrewMap() {
        Map<String, WorkerDomain> result = new HashMap<>();
        result.put(ticketOffice.getHeadOfOffice().getWorkerType().getDescription(), ticketOffice.getHeadOfOffice());
        return result;
    }

    public ObjectCollector getTicketOffice() {
        return ticketOffice;
    }

    @Override
    protected void doAddWorker(WorkerDomain worker) {
        if (worker instanceof InnerWorkerDomain innerWorkerDomain) {
            ticketOffice.setHeadOfOffice(innerWorkerDomain);
        } else {
            throw new IllegalArgumentException("Expected InnerWorkerDomain.class; Got: " +
                    worker.getClass().getSimpleName());
        }
    }

    //ADD FOR THROWING ILLEGALSTATE WHEN DUPLICATE
    @Override
    protected void doAddRevisionObject(RevisionObject o) {
        if (o instanceof TicketTerminal termial) {
            ticketOffice.getObjectsMap().put(o.getNumber(), termial);
        } else {
            throw new IllegalArgumentException("Expected TicketTerminal.class; Got: "
                    + o.getClass().getSimpleName());
        }
    }
}
