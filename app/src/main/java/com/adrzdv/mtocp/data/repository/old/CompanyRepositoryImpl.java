package com.adrzdv.mtocp.data.repository.old;

import com.adrzdv.mtocp.data.db.dao.CompanyDao;
import com.adrzdv.mtocp.data.db.entity.CompanyEntity;
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch;
import com.adrzdv.mtocp.domain.repository.old.CompanyRepository;

import java.util.List;

@Deprecated
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyDao dao;

    public CompanyRepositoryImpl(CompanyDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CompanyWithBranch> getAll() {
        return dao.getAll();
    }

    @Override
    public void saveAll(List<CompanyEntity> entities) {
        dao.insertAll(entities);
    }

    @Override
    public List<CompanyWithBranch> getDinnerCompanies() {
        return dao.getAllDinnerCompany();
    }


}
