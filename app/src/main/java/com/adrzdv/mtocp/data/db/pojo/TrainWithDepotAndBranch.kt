package com.adrzdv.mtocp.data.db.pojo

import androidx.room.Embedded
import com.adrzdv.mtocp.data.db.entity.BranchEntity
import com.adrzdv.mtocp.data.db.entity.DepotEntity
import com.adrzdv.mtocp.data.db.entity.TrainEntity

data class TrainWithDepotAndBranch(
    @Embedded
    val train: TrainEntity,
    @Embedded(prefix = "dpt_")
    val depot: DepotEntity,
    @Embedded(prefix = "br_")
    val branch: BranchEntity
)