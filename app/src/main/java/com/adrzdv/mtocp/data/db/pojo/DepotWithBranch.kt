package com.adrzdv.mtocp.data.db.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.adrzdv.mtocp.data.db.entity.BranchEntity
import com.adrzdv.mtocp.data.db.entity.DepotEntity

data class DepotWithBranch(
    @Embedded val depot: DepotEntity,

    @Relation(
        parentColumn = "branch_id",
        entityColumn = "id"
    )
    val branch: BranchEntity
)
