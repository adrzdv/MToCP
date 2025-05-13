package com.adrzdv.mtocp.data.repository;

import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;

import java.util.List;

import javax.inject.Inject;

public class ViolationRepositoryImpl implements ViolationRepository {

    private final ViolationDao violationDao;

    @Inject
    public ViolationRepositoryImpl(ViolationDao violationDao) {
        this.violationDao = violationDao;
    }

    @Override
    public List<ViolationEntity> getAll() {
        return violationDao.getAll();
    }
}
