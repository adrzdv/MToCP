package com.adrzdv.mtocp.domain.repository;

import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;

import java.util.List;

public interface TrainRepository {

    List<TrainEntity> getAll();

    TrainDomain getTrain(String str);

    void saveAll(List<TrainEntity> entities);
}
