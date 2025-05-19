package com.adrzdv.mtocp.data.repository;

import android.database.sqlite.SQLiteConstraintException;

import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;

import java.util.List;

public class ViolationRepositoryImpl implements ViolationRepository {

    private final ViolationDao violationDao;

    public ViolationRepositoryImpl(ViolationDao violationDao) {
        this.violationDao = violationDao;
    }

    @Override
    public List<ViolationEntity> getAll() {
        return violationDao.getAll();
    }

    @Override
    public void addNew(ViolationEntity violation) {
        violationDao.addNew(violation);
    }

    @Override
    public void updateByCode(ViolationEntity violation) throws SQLiteConstraintException {

        violationDao.updateByCode(violation.getCode(),
                violation.getName(),
                violation.getShortName(),
                violation.getActive(),
                violation.getInTransit(),
                violation.getAtStartPoint(),
                violation.getAtTurnroundPoint(),
                violation.getAtTicketOffice());
    }

    @Override
    public void saveAll(List<ViolationEntity> list) {
        violationDao.insertAll(list);
    }


}
