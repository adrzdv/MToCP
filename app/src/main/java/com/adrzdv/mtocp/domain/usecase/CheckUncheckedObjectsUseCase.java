package com.adrzdv.mtocp.domain.usecase;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;

import java.util.Map;

public class CheckUncheckedObjectsUseCase {

    public boolean execute(Map<String, RevisionObject> revObj) {
        for (RevisionObject obj : revObj.values()) {
            if (obj.getRevisionDateEnd() == null) {
                return true;
            }
        }
        return false;
    }
}
