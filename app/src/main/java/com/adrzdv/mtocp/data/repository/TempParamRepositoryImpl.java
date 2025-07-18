package com.adrzdv.mtocp.data.repository;

import com.adrzdv.mtocp.data.db.dao.TempParamsDao;
import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;
import com.adrzdv.mtocp.domain.repository.TempParamRepository;

import java.util.List;

public class TempParamRepositoryImpl implements TempParamRepository {

    private TempParamsDao dao;

    public TempParamRepositoryImpl(TempParamsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<TempParametersEntity> getAll() {
        return dao.getAll();
    }

    @Override
    public void saveAll(List<TempParametersEntity> entities) {
        dao.insertAll(entities);
    }
}
