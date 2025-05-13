package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;

public class ViolationMapper {

    public static ViolationDomain fromEntityToDomain(ViolationEntity violation) {
        return new ViolationDomain(violation.getCode(),
                violation.getName());
    }
}
