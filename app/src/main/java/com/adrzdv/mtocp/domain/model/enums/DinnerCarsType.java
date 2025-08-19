package com.adrzdv.mtocp.domain.model.enums;

public enum DinnerCarsType {
    BISTRO("Вагон-бистро"),
    RESTAURANT("Вагон-ресторан");

    private final String description;

    DinnerCarsType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
