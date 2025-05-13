package com.adrzdv.mtocp.domain.model.revisionobject.collectors;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.workers.Worker;

import java.util.Map;

public class Train extends ObjectCollector {

    private String route;
    private Map<String, Worker> workerMap;
    private Map<String, RevisionObject> coachMap;

    public Train(String number) {
        super(number);
    }

    public Map<String, RevisionObject> getCoachMap() {
        return coachMap;
    }

    public void setCoachMap(Map<String, RevisionObject> coachMap) {
        this.coachMap = coachMap;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Map<String, Worker> getWorkerMap() {
        return workerMap;
    }

    public void setWorkerMap(Map<String, Worker> workerMap) {
        this.workerMap = workerMap;
    }
}
