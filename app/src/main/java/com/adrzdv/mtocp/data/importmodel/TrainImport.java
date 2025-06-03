package com.adrzdv.mtocp.data.importmodel;

import java.util.Objects;

public class TrainImport {
    private Integer id;
    private String number;
    private String route;
    private Integer depotId;
    private Boolean isVideo;
    private Boolean isProgressive;

    public TrainImport(Integer id,
                       String number,
                       String route,
                       Integer depotId,
                       Boolean isVideo,
                       Boolean isProgressive) {
        this.id = id;
        this.number = number;
        this.route = route;
        this.depotId = depotId;
        this.isVideo = isVideo;
        this.isProgressive = isProgressive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TrainImport that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) && Objects.equals(depotId, that.depotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, depotId);
    }
}
