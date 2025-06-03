package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;

import java.time.LocalDateTime;

public class TicketOfficeOrder extends Order implements CollectableOrder {

    private ObjectCollector ticketOffice;

    public TicketOfficeOrder(String numberOrder,
                             LocalDateTime revisionDateStart,
                             LocalDateTime getRevisionDateEnd) {
        super(numberOrder, revisionDateStart, getRevisionDateEnd);
    }

    @Override
    public void setCollector(ObjectCollector collector) {
        this.ticketOffice = collector;
    }
}
