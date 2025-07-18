package com.adrzdv.mtocp.domain.model.revisionobject.collectors;

import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType;
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Domain class model for train
 */
public class TrainDomain extends ObjectCollector {

    private String route;
    private Map<String, WorkerDomain> workerMap;
    private DepotDomain depot;
    private Boolean isVideo;
    private Boolean isProgressive;
    private Boolean isDinnerCar;

    public TrainDomain(String number,
                       String route,
                       DepotDomain depot,
                       Boolean isVideo,
                       Boolean isProgressive,
                       Boolean isDinnerCar) {
        super(number);
        this.route = route;
        this.workerMap = new HashMap<>();
        this.depot = depot;
        this.isVideo = isVideo;
        this.isProgressive = isProgressive;
        this.isDinnerCar = isDinnerCar;
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

    public Boolean getDinnerCar() {
        return isDinnerCar;
    }

    public void setDinnerCar(Boolean dinnerCar) {
        isDinnerCar = dinnerCar;
    }

    public void addWorker(WorkerDomain workerDomain) {
        workerMap.put(workerDomain.getWorkerType().getDescription(), workerDomain);
    }

    public boolean checkAllCrewIsAdded() {
        return workerMap.containsKey(WorkerTypes.TRAIN_MANAGER.getDescription())
                && workerMap.containsKey(WorkerTypes.MECHANIC.getDescription());
    }

    public void clearCrewMap() {
        workerMap.clear();
    }

    public void deleteCreWorker(WorkerDomain worker) {
        workerMap.remove(worker.getWorkerType().getDescription());
    }

    public int countPassCoachType(PassengerCoachType type) {
        return (int) getObjectsMap().values().stream()
                .filter(o -> o instanceof PassengerCar)
                .map(o -> (PassengerCar) o)
                .filter(car -> car.getCoachType() == type)
                .count();

    }

    public void removeDinnerCar() {
        if (isDinnerCar) {
            getObjectsMap().values()
                    .removeIf(revisionObject ->
                            revisionObject.getNumber().contains("-6"));
        } else {
            throw new IllegalStateException(MessageCodes.PARAMETER_ERROR.getErrorTitle());
        }
    }

    @Override
    public String toString() {
        return getNumber() + " " + getRoute();
    }


}
