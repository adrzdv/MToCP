package com.adrzdv.mtocp.domain.model.enums;

/**
 * Enum of passenger coaches types
 */
public enum PassengerCoachType {
    INTERREGIONAL("МО"),
    OPEN_CLASS_SLEEPING("ПЛ"),
    COMPARTMENT("К"),
    FIRST_CLASS_SLEEPER("СВ"),
    LUXURY("ЛЮКС");

    private final String passengerCoachTypeTitle;

    PassengerCoachType(String passengerCoachTypeTitle) {
        this.passengerCoachTypeTitle = passengerCoachTypeTitle;
    }

    public String getPassengerCoachTitle() {
        return passengerCoachTypeTitle;
    }

    public static PassengerCoachType fromString(String passengerCoachTypeTitle) {
        for (PassengerCoachType revisionObject : PassengerCoachType.values()) {
            if (revisionObject.passengerCoachTypeTitle.equalsIgnoreCase(passengerCoachTypeTitle)) {
                return revisionObject;
            }
        }
        throw new IllegalArgumentException("Неизвестный тип вагона: " + passengerCoachTypeTitle);
    }

}
