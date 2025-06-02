package com.adrzdv.mtocp.mapper;

import com.adrzdv.mtocp.data.db.entity.CompanyEntity;
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch;
import com.adrzdv.mtocp.data.importmodel.CompanyImport;
import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;
import com.adrzdv.mtocp.ui.model.CompanyDto;

public class CompanyMapper {

    public static CompanyDomain fromPojoToDomain(CompanyWithBranch company) {

        return new CompanyDomain(company.company.getEls(),
                company.company.getName(),
                company.company.getActive(),
                company.company.getContractNumber(),
                company.company.getExpirationDate(),
                BranchMapper.fromEntityToDomain(company.branch));
    }

    public static CompanyDto fromDomainToDto(CompanyDomain company) {
        return new CompanyDto(company.getEls(),
                company.getName(),
                company.getActive(),
                company.getContractNumber(),
                company.getExpirationDate(),
                company.getBranchDomain().getShortName());
    }

    public static CompanyEntity fromImportToEntity(CompanyImport company) {
        return new CompanyEntity(company.getEls(),
                company.getName(),
                company.getActive(),
                company.getContract(),
                company.getExpirationDate(),
                company.getBranchId());
    }
}
