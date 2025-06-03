package com.adrzdv.mtocp.domain.model.order;

import java.time.LocalDateTime;

public abstract class Order {
    private String number;
    private LocalDateTime revisionDateStart;
    private LocalDateTime getRevisionDateEnd;


    public Order(String number, LocalDateTime revisionDateStart, LocalDateTime getRevisionDateEnd) {
        this.number = number;
        this.revisionDateStart = revisionDateStart;
        this.getRevisionDateEnd = getRevisionDateEnd;
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
}
