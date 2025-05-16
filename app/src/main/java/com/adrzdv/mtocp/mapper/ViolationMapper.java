package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.ui.model.ViolationDto;

public class ViolationMapper {

    public static ViolationDomain fromEntityToDomain(ViolationEntity violation) {
        return new ViolationDomain(violation.getCode(),
                violation.getName());
    }

    public static ViolationDto fromDomainToDto(ViolationDomain violation) {
        return new ViolationDto(violation.getCode(),
                violation.getName());
    }

    public static ViolationDomain fromDtoToDomain(ViolationDto violation) {
        return new ViolationDomain(violation.getCode(),
                violation.getName());
    }

    public static ViolationDto fromEntityToDto(ViolationEntity violation) {
        return new ViolationDto(violation.getCode(),
                violation.getName());
    }
}
