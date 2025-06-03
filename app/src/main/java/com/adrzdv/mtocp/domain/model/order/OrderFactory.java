package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.enums.OrdersTypes;

import java.time.LocalDateTime;

public class OrderFactory {
    public static Order createOrder(OrdersTypes types,
                                    String orderNumber,
                                    LocalDateTime dateStart,
                                    LocalDateTime dateEnd) {
        return switch (types) {
            case BAGGAGE_CAR -> new BaggageOrder(orderNumber, dateStart, dateEnd);
            case TICKET_OFFICE -> new TicketOfficeOrder(orderNumber, dateStart, dateEnd);
            case PASSENGER_TRAIN -> new TrainOrder(orderNumber, dateStart, dateEnd);
        };
    }
}
