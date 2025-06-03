package com.adrzdv.mtocp.domain.model.revisionobject.collectors;

/**
 * Domain class model for ticket office - ticket terminals collector
 */
public class TicketOffice extends ObjectCollector {

    private String stationName;

    public TicketOffice(String name) {
        super(name);
    }
}
