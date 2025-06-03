package com.adrzdv.mtocp.domain.model.enums;

import com.adrzdv.mtocp.MessageCodes;

import java.util.Arrays;
import java.util.List;

public enum OrdersTypes {
    PASSENGER_TRAIN("Проверка поезда"),
    BAGGAGE_CAR("Проверка вагона"),
    TICKET_OFFICE("Проверка кассы");

    private final String subscription;

    OrdersTypes(String subs) {
        this.subscription = subs;
    }

    public String getSubscription() {
        return subscription;
    }

    public static List<String> getTypeList() {
        return Arrays.stream(OrdersTypes.values())
                .map(OrdersTypes::getSubscription)
                .toList();
    }

    public static OrdersTypes getFromString(String string) {
        for (OrdersTypes type : OrdersTypes.values()) {
            if (type.getSubscription().equals(string)) {
                return type;
            }
        }
        throw new IllegalArgumentException(MessageCodes.INPUT_ERROR + string);
    }
}
