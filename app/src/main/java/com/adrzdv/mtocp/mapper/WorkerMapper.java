package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes;
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;
import com.adrzdv.mtocp.ui.model.InnerWorkerDto;
import com.adrzdv.mtocp.ui.model.dto.WorkerUI;

public class WorkerMapper {
    public static InnerWorkerDto fromDomainToDtoInner(InnerWorkerDomain worker) {

        return new InnerWorkerDto(worker.getId(),
                worker.getName(),
                worker.getWorkerType().getDescription(),
                worker.getDepotDomain().getShortName());

    }

    @Deprecated(forRemoval = true)
    public static WorkerUI fromDomainToUI(WorkerDomain workerDomain) {
        return new WorkerUI(workerDomain.toString(),
                workerDomain.getName(),
                workerDomain.getWorkerType().getDescription(),
                null);
    }

    @Deprecated(forRemoval = true)
    public static WorkerDomain fromUiToDomain(WorkerUI worker, DepotDomain depot) {
        return new InnerWorkerDomain(Integer.getInteger(worker.getId()),
                worker.getName(),
                depot,
                WorkerTypes.fromString(worker.getPosition()));
    }
}
