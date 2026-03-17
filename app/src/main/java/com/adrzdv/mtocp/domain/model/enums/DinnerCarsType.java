package com.adrzdv.mtocp.domain.model.enums;

import org.jetbrains.annotations.NotNull;

public enum DinnerCarsType {
    BISTRO("Вагон-бистро"),
    RESTAURANT("Вагон-ресторан");

    private final String description;

    DinnerCarsType(String description) {
        this.description = description;
    }

    public static DinnerCarsType fromString(@NotNull String type) {
        for (DinnerCarsType value : DinnerCarsType.values()) {
            if (value.description.equals(type)) {
                return value;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
}
