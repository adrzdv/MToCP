package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.ui.model.dto.WorkerUI

fun WorkerUI.toInnerDomain(depot: DepotDomain) = InnerWorkerDomain(
    id.toInt(), name, depot, WorkerTypes.fromString(position)
)

