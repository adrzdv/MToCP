package com.adrzdv.mtocp.data.repository;

import com.adrzdv.mtocp.data.db.dao.DepotDao;
import com.adrzdv.mtocp.data.db.dao.TrainDao;
import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;
import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;
import com.adrzdv.mtocp.domain.repository.TrainRepository;
import com.adrzdv.mtocp.mapper.DepotMapper;
import com.adrzdv.mtocp.mapper.TrainMapper;

import java.util.List;

public class TrainRepositoryImpl implements TrainRepository {

    private final TrainDao dao;
    private final DepotDao depotDao;

    public TrainRepositoryImpl(TrainDao dao, DepotDao depotDao) {
        this.dao = dao;
        this.depotDao = depotDao;
    }

    @Override
    public List<TrainEntity> getAll() {
        return dao.getAll();
    }

    @Override
    public TrainDomain getTrain(String str) {

        TrainEntity trainEntity = dao.getTrainByString(str);
        DepotWithBranch depotPojo = depotDao.getDepotById(trainEntity.getDepotId());
        DepotDomain depotDomain = DepotMapper.fromJoinModelToDomain(depotPojo);

        return TrainMapper.fromEntityToDomain(trainEntity, depotDomain);
    }

    @Override
    public void saveAll(List<TrainEntity> entities) {
        dao.insertAll(entities);
    }
}
