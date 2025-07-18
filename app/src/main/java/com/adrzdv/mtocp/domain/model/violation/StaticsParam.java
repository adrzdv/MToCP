package com.adrzdv.mtocp.domain.model.violation;

public class StaticsParam {
    private int id;
    private String name;
    private Boolean isCompleted;
    private String note;

    public StaticsParam() {

    }

    public StaticsParam(int id, String name, Boolean isCompleted, String note) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
