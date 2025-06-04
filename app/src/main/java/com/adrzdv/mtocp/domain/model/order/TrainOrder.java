package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;

import java.time.LocalDateTime;

public class TrainOrder extends Order implements CollectableOrder {
    private String route;
    private ObjectCollector train;

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

    public ObjectCollector getTrain() {
        return train;
    }

    @Override
    public void setCollector(ObjectCollector collector) {
        this.train = collector;
    }
}
