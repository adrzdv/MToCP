package com.adrzdv.mtocp.data.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class DepotWithBranch {
    @Embedded
    public DepotEntity depot;

    @Relation(
            parentColumn = "branchId",
            entityColumn = "id"
    )
    public BranchEntity branch;
}