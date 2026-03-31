package com.adrzdv.mtocp.data.db.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.DepotEntity;

public class DepotWithBranch {
    @Embedded
    public DepotEntity depot;

    @Relation(
            parentColumn = "branch_id",
            entityColumn = "id"
    )
    public BranchEntity branch;
}