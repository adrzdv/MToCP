package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain;
import com.adrzdv.mtocp.ui.model.InnerWorkerDto;

public class WorkerMapper {
    public static InnerWorkerDto fromDomainToDtoInner(InnerWorkerDomain worker) {

        return new InnerWorkerDto(worker.getId(),
                worker.getName(),
                worker.getWorkerType().getDescription(),
                worker.getDepot().getShortName());

    }
}
