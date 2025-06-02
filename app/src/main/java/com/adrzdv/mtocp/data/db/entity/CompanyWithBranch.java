package com.adrzdv.mtocp.data.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CompanyWithBranch {

    @Embedded
    public CompanyEntity company;
    @Relation(
            parentColumn = "id_branch",
            entityColumn = "id"
    )
    public BranchEntity branch;
}
