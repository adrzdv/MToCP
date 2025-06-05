package com.adrzdv.mtocp.ui.model;

public class InnerWorkerDto {
    private int id;
    private String name;
    private String type;
    private String depot;

    public InnerWorkerDto(int id, String name, String type, String depot) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.depot = depot;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }
}
