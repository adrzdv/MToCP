package com.adrzdv.mtocp.ui.model;

public class TrainDto {
    private String number;
    private String route;
    private String depotName;
    private String depotShortName;
    private String branchName;
    private String branchShortName;
    private String phone;
    private Boolean isVideo;
    private Boolean isProgressive;
    private Boolean isDinnerCar;


    public TrainDto(String number,
                    String route,
                    String depotName,
                    String depotShortName,
                    String branchName,
                    String branchShortName,
                    String phone,
                    Boolean isVideo,
                    Boolean isProgressive,
                    Boolean isDinnerCar) {
        this.number = number;
        this.route = route;
        this.depotName = depotName;
        this.depotShortName = depotShortName;
        this.branchName = branchName;
        this.branchShortName = branchShortName;
        this.phone = phone;
        this.isVideo = isVideo;
        this.isProgressive = isProgressive;
        this.isDinnerCar = isDinnerCar;
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

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getDepotShortName() {
        return depotShortName;
    }

    public void setDepotShortName(String depotShortName) {
        this.depotShortName = depotShortName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchShortName() {
        return branchShortName;
    }

    public void setBranchShortName(String branchShortName) {
        this.branchShortName = branchShortName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Boolean getDinnerCar() {
        return isDinnerCar;
    }

    public void setDinnerCar(Boolean dinnerCar) {
        isDinnerCar = dinnerCar;
    }
}
