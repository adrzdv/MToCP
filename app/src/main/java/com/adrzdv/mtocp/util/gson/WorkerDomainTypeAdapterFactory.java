package com.adrzdv.mtocp.util.gson;

import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.OuterWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

public class WorkerDomainTypeAdapterFactory {
    public static RuntimeTypeAdapterFactory<WorkerDomain> create() {
        return RuntimeTypeAdapterFactory
                .of(WorkerDomain.class, "workerClass")
                .registerSubtype(InnerWorkerDomain.class, InnerWorkerDomain.class.getSimpleName())
                .registerSubtype(OuterWorkerDomain.class, OuterWorkerDomain.class.getSimpleName());
    }
}
