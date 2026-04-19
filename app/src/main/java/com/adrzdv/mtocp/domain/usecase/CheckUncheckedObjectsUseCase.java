package com.adrzdv.mtocp.domain.usecase;

import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;

import java.util.Map;
import java.util.UUID;

public class CheckUncheckedObjectsUseCase {

    public boolean execute(Map<UUID, RevisionObject> revObj) {
        for (RevisionObject obj : revObj.values()) {
            if (obj.getRevisionDateEnd() == null) {
                return true;
            }
        }
        return false;
    }
}
