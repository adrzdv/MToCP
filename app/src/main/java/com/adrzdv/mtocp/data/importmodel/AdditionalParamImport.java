package com.adrzdv.mtocp.data.importmodel;

import java.time.LocalDate;

public class AdditionalParamImport {
    private Integer id;
    private String name;
    private Boolean isActive;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Boolean isTrain;

    public AdditionalParamImport(Integer id,
                                 String name,
                                 Boolean isActive,
                                 LocalDate dateStart,
                                 LocalDate dateEnd,
                                 Boolean isTrain) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isTrain = isTrain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Boolean getTrain() {
        return isTrain;
    }

    public void setTrain(Boolean train) {
        isTrain = train;
    }
}
