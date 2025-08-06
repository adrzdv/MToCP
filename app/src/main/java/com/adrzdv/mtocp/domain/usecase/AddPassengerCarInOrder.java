package com.adrzdv.mtocp.domain.usecase;

import com.adrzdv.mtocp.domain.model.order.Order;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;
import com.adrzdv.mtocp.ui.state.CoachFormState;

@Deprecated
public class AddPassengerCarInOrder {
    public CoachFormState execute(Order order, PassengerCar coach) {
        if (coach.getDepotDomain() == null) {
            return new CoachFormState.Error(CoachFormState.ErrorType.INVALID_DEPOT);
        }
        if (coach.getCoachRoute() == null || coach.getCoachRoute().isBlank()) {
            return new CoachFormState.Error(CoachFormState.ErrorType.INVALID_ROUTE);
        }
        try {
            order.addRevisionObject(coach);
            return new CoachFormState.Success();
        } catch (IllegalArgumentException e) {
            return new CoachFormState.Error(CoachFormState.ErrorType.PATTERN_ERROR);
        } catch (IllegalStateException e) {
            return new CoachFormState.Error(CoachFormState.ErrorType.DUPLICATE_ERROR);
        }
    }
}
