package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.data.importmodel.TrainImport;
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
}
