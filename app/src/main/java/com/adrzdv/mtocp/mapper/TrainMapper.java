package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.data.db.pojo.TrainWithDepotAndBranch;
import com.adrzdv.mtocp.data.importmodel.TrainImport;
import com.adrzdv.mtocp.domain.model.departments.BranchDomain;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;
import com.adrzdv.mtocp.ui.model.TrainDto;

public class TrainMapper {

    public static TrainDomain fromEntityToDomain(TrainEntity trainEntity,
                                                 DepotDomain depotDomain) {
        return new TrainDomain(trainEntity.getNumber(),
                trainEntity.getRoute(),
                depotDomain,
                trainEntity.getVideo(),
                trainEntity.getProgressive(),
                false);
    }

    public static TrainEntity fromImportToEntity(TrainImport train) {

        return new TrainEntity(train.getId(),
                train.getNumber(),
                train.getRoute(),
                train.getDepotId(),
                train.getVideo(),
                train.getProgressive());
    }

    public static TrainDto fromDomainToDto(TrainDomain train) {

        return new TrainDto(train.getNumber(),
                train.getRoute(),
                train.getDepot().getName(),
                train.getDepot().getShortName(),
                train.getDepot().getBranchDomain().getName(),
                train.getDepot().getBranchDomain().getShortName(),
                train.getDepot().getPhoneNumber(),
                train.getVideo(),
                train.getProgressive(),
                train.getIsDinnerCar());
    }

    public static TrainDomain fromTrainWithDepotAndBranchToDomain(TrainWithDepotAndBranch pojo) {

        BranchDomain branch = new BranchDomain(pojo.getBranch().getId(),
                pojo.getBranch().getName(),
                pojo.getBranch().getShortName());

        DepotDomain depot = new DepotDomain(pojo.getDepot().getId(),
                pojo.getDepot().getName(),
                pojo.getDepot().getShortName(),
                pojo.getDepot().getPhoneNumber(),
                branch,
                pojo.getDepot().getActive(),
                pojo.getDepot().getDinnerDepot());

        return new TrainDomain(pojo.getTrain().getNumber(),
                pojo.getTrain().getRoute(),
                depot,
                pojo.getTrain().getVideo(),
                pojo.getTrain().getProgressive(),
                false);
    }
}
