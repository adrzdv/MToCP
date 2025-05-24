package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;
import com.adrzdv.mtocp.data.importmodel.BranchImport;
import com.adrzdv.mtocp.domain.model.departments.BranchDomain;

public class BranchMapper {

    public static BranchDomain fromEntityToDomain(BranchEntity branch) {
        return new BranchDomain(branch.getId(),
                branch.getName(),
                branch.getShortName());
    }

    public static BranchEntity fromPOJOToDomain(DepotWithBranch depot) {
        return new BranchEntity(depot.branch.getId(),
                depot.branch.getName(),
                depot.branch.getShortName(),
                depot.branch.getAlive());
    }

    public static BranchEntity fromImportToEntity(BranchImport branch) {
        return new BranchEntity(branch.getId(),
                branch.getName(),
                branch.getShortName(),
                branch.getActive());
    }
}
