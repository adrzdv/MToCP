package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.enums.CoachTypes;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;

import org.jspecify.annotations.Nullable;

/**
 * Domain class model for abstract coach object.
 * Stores data about static parameters in coach.
 * CoachRoute field is un-required field, using only for trailing cars in passenger trains
 * or for collecting baggage car routes
 */
public abstract class Coach extends RevisionObject {
    @Nullable
    private String coachRoute;
    private CoachTypes typeGlobal;

    public Coach(String name, CoachTypes type) {
        super(name);
        coachRoute = "";
        typeGlobal = type;
    }

    public @Nullable String getCoachRoute() {
        return coachRoute;
    }

    public void setCoachRoute(@Nullable String coachRoute) {
        this.coachRoute = coachRoute;
    }

    public CoachTypes getTypeGlobal() {
        return typeGlobal;
    }

    public void setTypeGlobal(CoachTypes typeGlobal) {
        this.typeGlobal = typeGlobal;
    }
}
