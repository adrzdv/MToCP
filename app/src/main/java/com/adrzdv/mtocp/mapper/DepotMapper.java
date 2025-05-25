package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.DepotEntity;
import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;
import com.adrzdv.mtocp.data.importmodel.DepotImport;
import com.adrzdv.mtocp.domain.model.departments.BranchDomain;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;

public class DepotMapper {

    public static DepotDomain fromPOJOEntityToDomain(DepotWithBranch depotWithBranch) {

        return new DepotDomain(depotWithBranch.depot.getId(),
                depotWithBranch.depot.getName(),
                depotWithBranch.depot.getShortName(),
                depotWithBranch.depot.getPhoneNumber(),
                new BranchDomain(depotWithBranch.branch.getId(),
                        depotWithBranch.branch.getName(),
                        depotWithBranch.branch.getShortName()),
                depotWithBranch.depot.getActive());

    }

    public static DepotEntity fromDomainToEntity(DepotDomain depot) {

        return new DepotEntity(depot.getId(),
                depot.getName(),
                depot.getShortName(),
                depot.getBranchDomain().getId(),
                depot.getActive(),
                depot.getPhoneNumber());
    }

    public static DepotEntity fromImportToEntity(DepotImport depot) {

        return new DepotEntity(depot.getId(),
                depot.getName(),
                depot.getShortName(),
                depot.getBranch().getId(),
                depot.getBranch().getActive(),
                depot.getPhoneNumber());
    }

    public static DepotWithBranch fromImportToPOJO(DepotImport depot) {

        return null;

    }
}
