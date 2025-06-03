package com.adrzdv.mtocp.domain.model.revisionobject.collectors;

import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.util.HashMap;
import java.util.Map;

/**
 * Domain class model for train
 */
public class TrainDomain extends ObjectCollector {

    private String route;
    private Map<String, WorkerDomain> workerMap;
    private DepotDomain depot;
    private Boolean isVideo;
    private Boolean isProgressive;

    public TrainDomain(String number,
                       String route,
                       DepotDomain depot,
                       Boolean isVideo,
                       Boolean isProgressive) {
        super(number);
        this.route = route;
        this.workerMap = new HashMap<>();
        this.depot = depot;
        this.isVideo = isVideo;
        this.isProgressive = isProgressive;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Map<String, WorkerDomain> getWorkerMap() {
        return workerMap;
    }

    public void setWorkerMap(Map<String, WorkerDomain> workerMap) {
        this.workerMap = workerMap;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public void setVideo(Boolean video) {
        isVideo = video;
    }

    public Boolean getProgressive() {
        return isProgressive;
    }

    public void setProgressive(Boolean progressive) {
        isProgressive = progressive;
    }

    public DepotDomain getDepot() {
        return depot;
    }

    public void setDepot(DepotDomain depot) {
        this.depot = depot;
    }
}
