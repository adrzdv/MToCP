package com.adrzdv.mtocp.domain.model.revisionobject.basic.coach;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.violation.StaticsParam;

import java.util.HashMap;
import java.util.Map;

public abstract class Coach extends RevisionObject {
    private Map<String, StaticsParam> staticParamsMap;

    public Coach(String name) {
        super(name);
        staticParamsMap = new HashMap<>();
    }

    public Map<String, StaticsParam> getStaticParamsMap() {
        return staticParamsMap;
    }

    public void setStaticParamsMap(Map<String, StaticsParam> staticParamsMap) {
        this.staticParamsMap = staticParamsMap;
    }
}
