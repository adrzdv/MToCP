package com.adrzdv.mtocp.mapper;

import androidx.annotation.NonNull;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.ui.model.ViolationDto;

public class ViolationMapper {

    public static ViolationDomain fromEntityToDomain(@NonNull ViolationEntity violation) {
        return new ViolationDomain(violation.getCode(),
                violation.getName(),
                violation.getShortName());
    }

    public static ViolationDto fromDomainToDto(@NonNull ViolationDomain violation) {
        return new ViolationDto(violation.getCode(),
                violation.getName());
    }

    public static ViolationDto fromEntityToDto(@NonNull ViolationEntity violation) {
        return new ViolationDto(violation.getCode(),
                violation.getName());
    }

    public static ViolationEntity fromImportToEntity(@NonNull ViolationImport violation) {

        ViolationEntity violationEntity = new ViolationEntity(violation.getCode(),
                violation.getName(),
                violation.getShortName(),
                violation.getActive(),
                violation.getInTransit(),
                violation.getAtStartPoint(),
                violation.getAtTurnroundPoint(),
                violation.getAtTicketOffice());

        violationEntity.setId(violation.getId());

        return violationEntity;
    }
}
