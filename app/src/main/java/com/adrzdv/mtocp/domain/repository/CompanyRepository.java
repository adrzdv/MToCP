package com.adrzdv.mtocp.domain.repository;

import com.adrzdv.mtocp.data.db.entity.CompanyEntity;
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch;

import java.util.List;


public interface CompanyRepository {
    List<CompanyWithBranch> getAll();

    void saveAll(List<CompanyEntity> entities);

    List<CompanyWithBranch> getDinnerCompanies();
}
