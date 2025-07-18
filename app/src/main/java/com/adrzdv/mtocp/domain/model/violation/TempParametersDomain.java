package com.adrzdv.mtocp.domain.model.violation;

import androidx.room.Ignore;

import java.time.LocalDate;
import java.util.Objects;

import kotlin.jvm.Transient;

public class TempParametersDomain {
    private Integer id;
    private String name;
    private Boolean isActive;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Boolean isTrain;
    private String note;

    public TempParametersDomain() {
        this.id = 0;
        this.name = "";
        this.isActive = false;
        this.dateStart = LocalDate.of(1970, 1, 1);
        this.dateEnd = LocalDate.of(1970, 1, 1);
        this.isTrain = false;
        this.note = "";
    }

    @Ignore
    public TempParametersDomain(int id,
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
        this.note = "";
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TempParametersDomain that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
