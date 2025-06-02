package com.adrzdv.mtocp.data.repository;

import com.adrzdv.mtocp.data.db.dao.CompanyDao;
import com.adrzdv.mtocp.data.db.entity.CompanyEntity;
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch;
import com.adrzdv.mtocp.domain.repository.CompanyRepository;

import java.util.List;

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
}
