package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;

public interface CollectableOrder {
    void setCollector(ObjectCollector collector);
}
