package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.violation.StaticsParam;

import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Domain class model for abstract coach object.
 * Stores data about static parameters in coach.
 * CoachRoute field is un-required field, using only for trailing cars in passenger trains
 * or for collecting baggage car routes
 */
public abstract class Coach extends RevisionObject {
    @Nullable
    private String coachRoute;

    public Coach(String name) {
        super(name);
        coachRoute = "";
    }

    public @Nullable String getCoachRoute() {
        return coachRoute;
    }

    public void setCoachRoute(@Nullable String coachRoute) {
        this.coachRoute = coachRoute;
    }
}
