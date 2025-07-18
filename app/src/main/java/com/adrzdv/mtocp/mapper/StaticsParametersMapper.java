package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;
import com.adrzdv.mtocp.domain.model.violation.StaticsParam;
import com.adrzdv.mtocp.domain.model.violation.TempParametersDomain;

public class StaticsParametersMapper {

    public static TempParametersDomain fromEntityToDomain(TempParametersEntity entity) {
        return new TempParametersDomain(entity.getId(),
                entity.getName(),
                entity.getActive(),
                entity.getDateStart(),
                entity.getDateEnd(),
                entity.getTrain());
    }

    public static StaticsParam fromDomainToShort(TempParametersDomain tempParam) {
        return new StaticsParam(tempParam.getId(),
                tempParam.getName(),
                null,
                tempParam.getNote());
    }
}
