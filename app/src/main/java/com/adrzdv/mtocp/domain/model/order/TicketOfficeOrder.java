package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;

import java.time.LocalDateTime;

public class TicketOfficeOrder extends Order implements CollectableOrder {

    private ObjectCollector ticketOffice;

    public TicketOfficeOrder(String numberOrder,
                             LocalDateTime revisionDateStart,
                             LocalDateTime getRevisionDateEnd,
                             String route) {
        super(numberOrder, revisionDateStart, getRevisionDateEnd, route);
    }

    @Override
    public void setCollector(ObjectCollector collector) {
        this.ticketOffice = collector;
    }

    @Override
    public ObjectCollector getCollector() {
        return ticketOffice;
    }

    public ObjectCollector getTicketOffice() {
        return ticketOffice;
    }
}
