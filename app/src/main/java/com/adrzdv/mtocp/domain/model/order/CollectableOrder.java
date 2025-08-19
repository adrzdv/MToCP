package com.adrzdv.mtocp.domain.model.order;

import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.util.Map;

/**
 * Represents an order that supports collection and management of violations
 * through an {@link ObjectCollector}. This interface provides methods to assign
 * a collector, check quality passport requirements, and add or remove violations
 * associated with specific object numbers.
 */
public interface CollectableOrder {

    /**
     * Assigns an {@link ObjectCollector} to this order.
     *
     * @param collector the collector to assign
     */
    void setCollector(ObjectCollector collector);

    /**
     * Retrieves the {@link ObjectCollector} assigned to this order.
     *
     * @return the assigned collector
     */
    ObjectCollector getCollector();

    /**
     * Sets whether this order includes a quality passport.
     *
     * @param isQualityPassport true if a quality passport is included; false otherwise
     */
    void setIsQualityPassport(Boolean isQualityPassport);

    /**
     * Get crew map
     *
     * @return map of worker domain
     */
    Map<String, WorkerDomain> getCrewMap();
}
